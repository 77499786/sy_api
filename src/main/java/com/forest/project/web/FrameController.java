package com.forest.project.web;

import com.forest.core.BaseController;
import com.forest.core.ProjectConstant;
import com.forest.core.Result;
import com.forest.core.ResultGenerator;
import com.forest.project.model.Attachfile;
import com.forest.project.model.FrameFiles;
import com.forest.project.model.FrameLog;
import com.forest.project.model.TreeData;
import com.forest.project.service.FrameEmployeeService;
import com.forest.project.service.FrameFilesService;
import com.forest.project.service.FrameLogService;
import com.forest.utils.DateUtils;
import com.forest.utils.FileUtils;
import com.forest.utils.ScsyReportUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/04/11.
 */
@RestController
@RequestMapping("/framemain")
public class FrameController extends BaseController {

    /**
     * 文件管理绝对位置
     */
    @Value("${scsy.baseabsoutepath}")
    private String filefolder;
    @Value("${scsy.basepath}")
    private String basepath;
    @Value("${scsy.uploadpath}")
    private String filepath;
//    private String upload_folder = "/upload";
    @Autowired
    private FrameFilesService frameFilesService;
    @Resource
    FrameLogService frameLogService;
    @Resource
    private FrameEmployeeService frameEmployeeService;

    @PostMapping("/savefile")
    public Result savefile(HttpServletRequest request) {
        super.getUserAccount();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            multipartRequest.setCharacterEncoding("UTF-8");
//            ReflectHelper reflectHelper = new ReflectHelper(uploadFile.getObject());
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            // 文件数据库保存路径
            String path =  basepath + filepath + "/";// 文件保存在硬盘的相对路径
            String realPath = filefolder + filepath + "/";// 文件的硬盘真实路径
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();// 创建根目录
            }
            List<String> files = new ArrayList<String>();
            String fileName = "";
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();// 获取上传文件对象
                fileName = mf.getOriginalFilename();// 获取文件名
                String extend = FileUtils.getExtend(fileName);// 获取文件扩展名
                String myfilename = "";
                String noextfilename = fileName.substring(0, fileName.lastIndexOf("."));// 不带扩展名
                myfilename = fileName;

                String savePath = realPath + myfilename;// 文件保存全路径
                String fileprefixName = FileUtils.getFilePrefix(fileName);
                File savefile = new File(savePath);
                // 文件拷贝到指定硬盘目录
                FileCopyUtils.copy(mf.getBytes(), savefile);

                // 保存文件信息
                FrameFiles frameFiles = new FrameFiles();
                frameFiles.setCreator(super._userid);
                frameFiles.setCreatetime(new Date());
                frameFiles.setModifer(super._userid);
                frameFiles.setModifytime(new Date());
                frameFiles.setRealpath(path.concat(myfilename));
                frameFiles.setExtend(extend);
                frameFiles.setAttachmenttitle(fileprefixName);
                frameFiles.setInuse(ProjectConstant.USE_STATUS.INUSE.getIndex());
                frameFilesService.save(frameFiles);
                files.add(path.concat(myfilename));
            }
            return ResultGenerator.genSuccessResult(files);
        } catch (Exception e1) {
            return ResultGenerator.genFailResult("文件上传失败！");
        }
    }

    @PostMapping("/listfiles")
    public Result list() {
        List<Attachfile> list = new ArrayList<Attachfile>();
        String path = basepath + "/";// 文件保存在硬盘的相对路径
        String realPath = filefolder +  "/";// 文件的硬盘真实路径
        File fd = new File(realPath);
        for (File file : fd.listFiles()) {
            list.add(new Attachfile(file.getName(), path + file.getName()));
        }
        PageInfo<Attachfile> pageInfo = new PageInfo<Attachfile>();
        pageInfo.setTotal(list.size());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/listtreefiles")
    public Result listtreefiles(HttpServletRequest req) {
        String folderpath = filefolder;
        if(!Strings.isNullOrEmpty(req.getParameter("path"))){
            folderpath = req.getParameter("path").replace(basepath, filefolder);
        }
        List<TreeData> filelist = FileUtils.getFolderExtends(folderpath);
        formatPath(filelist);
        return ResultGenerator.genSuccessResult(filelist);
    }

    @PostMapping("/getlog")
    public Result getlog(@RequestBody() @Valid PageInfo<FrameLog> pageInfo, BindingResult bindingResult) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize(), true);
        Condition condition = new Condition(FrameLog.class);
        // 根据页面的查询条件编辑查询条件
        String keyword = super.formatString(super.getItem(pageInfo, "keyword"));
        if (!Strings.isNullOrEmpty(keyword)) {
            condition.createCriteria()
                    .orLike("ipaddress", "%" + keyword + "%")
                    .orLike("requesturl", "%" + keyword + "%")
                    .orLike("operator", "%" + keyword + "%");
        }
        condition.orderBy("operatetime").desc();

        List<FrameLog> list = frameLogService.findByCondition(condition);
        pageInfo.setTotal(page.getTotal());
        pageInfo.setList(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/getnow")
    public Result getnow(HttpServletRequest req) {
        return ResultGenerator.genSuccessResult(DateUtils.formatTime());
    }

    @PostMapping("/getuser")
    public Result getuser(HttpServletRequest req) {
        super.getUserAccount();
        return ResultGenerator.genSuccessResult(_user);
    }

    private void formatPath(List<TreeData> files){
        files.forEach( f->{
            if (f.getChildren()!= null && f.getChildren().size() > 0) {
                f.setId(f.getId().replace(filefolder, basepath.concat("/")));
                formatPath(f.getChildren());
            } else {
                f.setId(f.getId().replace(filefolder, basepath.concat("/")));
            }
        });
    }
}
