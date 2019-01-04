public class DataBean {
    private String jybh;
    private String ypmc;

    public DataBean(String jybh, String ypmc){
        this.jybh = jybh;
        this.ypmc = ypmc;
    }
    public String getJybh( ) {
        return jybh;
    }

    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    public String getYpmc() {
        return ypmc;
    }

    public void setYpmc(String ypmc) {
        this.ypmc = ypmc;
    }
}
