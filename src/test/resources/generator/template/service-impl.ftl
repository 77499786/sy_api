package ${basePackage}.${subPackage}service.impl;

import ${basePackage}.${subPackage}dao.${modelNameUpperCamel}Mapper;
import ${basePackage}.${subPackage}model.${modelNameUpperCamel};
import ${basePackage}.${subPackage}service.${modelNameUpperCamel}Service;
import ${basePackage}.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    // 需要手工追加查询逻辑才添加
    //Resource
    //private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
