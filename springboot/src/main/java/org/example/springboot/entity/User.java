package org.example.springboot.entity;


import java.util.Date;


public class User {
    /** 用户ID */
    private Integer user_id;
    /** 部门ID */
    private Integer dept_id;
    /** 用户账号 */
    private String user_name;
    /** 用户昵称 */
    private String nick_name;
    /** 用户类型（00系统用户） */
    private String user_type;
    /** 用户邮箱 */
    private String email;
    /** 手机号码 */
    private String phonenumber;
    /** 用户性别（0男 1女 2未知） */
    private String sex;
    /** 头像地址 */
    private String avatar;
    /** 密码 */
    private String password;
    /** 账号状态（0正常 1停用） */
    private String status;
    /** 删除标志（0代表存在 2代表删除） */
    private String del_flag;
    /** 最后登录IP */
    private String login_ip;
    /** 最后登录时间 */
    private Date login_date;
    /** 密码最后更新时间 */
    private Date pwd_update_date;
    /** 创建者 */
    private String create_by;
    /** 创建时间 */
    private Date create_time;
    /** 更新者 */
    private String update_by;
    /** 更新时间 */
    private Date update_time;
    /** 备注 */
    private String remark;

    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_d) {
        this.dept_id = dept_d;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }

    public Date getPwd_update_date() {
        return pwd_update_date;
    }

    public void setPwd_update_date(Date pwd_update_date) {
        this.pwd_update_date = pwd_update_date;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_by() {
        return update_by;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}