package com.tao.xiaoyuanyuan.db.entity;

/**
 * Created by Dian on 2018/12/23
 * 个人登录返回类
 */

public class LPersonUserBean {


    public LPersonUserBean() {
    }

    /**
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NTU0Njc5ODEsInRpbWUiOjE1NTUzODE1ODEsInR5cGUiOjIsImNlbGxwaG9uZSI6IjE1OTc0MjU1MDEzIn0.97Gv9Jijd3PVsEBxfBBi5OB_3_NocBTCRuVaI-HRqxc
     * user_info : {"user_id":23,"cellphone":"15974255013","password":"5de00874503ec8ef1f7b4fee0a8879eb","salt":"abeghjmrsvxzBCGILMNQRSTWXZ125679",
     * "state":1,"dept_id":1,"is_delete":0,"create_time":"2019-03-20 18:51:39","update_time":null,"type":2,"last_time":"2019-04-16 10:26:21",
     * "login_num":111,"last_ip":"192.168.8.118","mini_open_id":null,"wx_open_id":null,"zfb_open_id":null,"area_code":null,"withdraw_pass":false,
     * "withdraw_salt":null,"zfb_account":null,"user_info":{"id":16,"user_id":23,"id_card":"430523199905147027",
     * "head_icon":"/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-04-02-11-46-33-239_com.paiwujie.robredpacket.png","
     * nickname":"刘大大","name":"夏天","sex":null,"age":null,"address":null,"business":"房地产","is_delete":0,"interest":"网购，游戏，编程","code":null,"office":null}}
     * menu :
     * permission :
     */

    private String token;
    private UserInfoBeanX user_info;
    private String menu;
    private String permission;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfoBeanX getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBeanX user_info) {
        this.user_info = user_info;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public static class UserInfoBeanX {
        public UserInfoBeanX() {
        }

        /**
         * user_id : 23
         * cellphone : 15974255013
         * password : 5de00874503ec8ef1f7b4fee0a8879eb
         * salt : abeghjmrsvxzBCGILMNQRSTWXZ125679
         * state : 1
         * dept_id : 1
         * is_delete : 0
         * create_time : 2019-03-20 18:51:39
         * update_time : null
         * type : 2
         * last_time : 2019-04-16 10:26:21
         * login_num : 111
         * last_ip : 192.168.8.118
         * mini_open_id : null
         * wx_open_id : null
         * zfb_open_id : null
         * area_code : null
         * withdraw_pass : false
         * withdraw_salt : null
         * zfb_account : null
         * user_info : {"id":16,"user_id":23,"id_card":"430523199905147027","head_icon":"/storage/emulated/0/DCIM/Screenshots/Screenshot_2019-04-02-11-46-33-239_com.paiwujie.robredpacket.png","nickname":"刘大大","name":"夏天","sex":null,"age":null,"address":null,"business":"房地产","is_delete":0,"interest":"网购，游戏，编程","code":null,"office":null}
         */

        private int user_id;
        private String cellphone;
        private String password;
        private String salt;
        private int state;
        private int dept_id;
        private int is_delete;
        private String create_time;
        private Object update_time;
        private int type;
        private String last_time;
        private int login_num;
        private String last_ip;
        private Object mini_open_id;
        private Object wx_open_id;
        private Object zfb_open_id;
        private Object area_code;
        private int withdraw_pass;
        private Object withdraw_salt;
        private Object zfb_account;
        private UserInfoBean user_info;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getDept_id() {
            return dept_id;
        }

        public void setDept_id(int dept_id) {
            this.dept_id = dept_id;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public int getLogin_num() {
            return login_num;
        }

        public void setLogin_num(int login_num) {
            this.login_num = login_num;
        }

        public String getLast_ip() {
            return last_ip;
        }

        public void setLast_ip(String last_ip) {
            this.last_ip = last_ip;
        }

        public Object getMini_open_id() {
            return mini_open_id;
        }

        public void setMini_open_id(Object mini_open_id) {
            this.mini_open_id = mini_open_id;
        }

        public Object getWx_open_id() {
            return wx_open_id;
        }

        public void setWx_open_id(Object wx_open_id) {
            this.wx_open_id = wx_open_id;
        }

        public Object getZfb_open_id() {
            return zfb_open_id;
        }

        public void setZfb_open_id(Object zfb_open_id) {
            this.zfb_open_id = zfb_open_id;
        }

        public Object getArea_code() {
            return area_code;
        }

        public void setArea_code(Object area_code) {
            this.area_code = area_code;
        }

        public int isWithdraw_pass() {
            return withdraw_pass;
        }

        public void setWithdraw_pass(int withdraw_pass) {
            this.withdraw_pass = withdraw_pass;
        }

        public Object getWithdraw_salt() {
            return withdraw_salt;
        }

        public void setWithdraw_salt(Object withdraw_salt) {
            this.withdraw_salt = withdraw_salt;
        }

        public Object getZfb_account() {
            return zfb_account;
        }

        public void setZfb_account(Object zfb_account) {
            this.zfb_account = zfb_account;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {

            /**
             * id : 16
             * user_id : 23
             * id_card : 430523199905147027
             * head_icon : /storage/emulated/0/DCIM/Screenshots/Screenshot_2019-04-02-11-46-33-239_com.paiwujie.robredpacket.png
             * nickname : 刘大大
             * name : 夏天
             * sex : null
             * age : null
             * address : null
             * business : 房地产
             * is_delete : 0
             * interest : 网购，游戏，编程
             * code : null
             * office : null
             * check_state : 0
             *
             */

            private int id;
            private int user_id;
            private String id_card;
            private String head_icon;
            private String nickname;
            private String name;
            private Object sex;
            private Object age;
            private Object address;
            private String business;
            private int is_delete;
            private String interest;
            private Object code;
            private Object office;
            private int check_state;

            public UserInfoBean() {
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getId_card() {
                return id_card;
            }

            public void setId_card(String id_card) {
                this.id_card = id_card;
            }

            public String getHead_icon() {
                return head_icon;
            }

            public void setHead_icon(String head_icon) {
                this.head_icon = head_icon;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getAge() {
                return age;
            }

            public void setAge(Object age) {
                this.age = age;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public String getBusiness() {
                return business;
            }

            public void setBusiness(String business) {
                this.business = business;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }

            public String getInterest() {
                return interest;
            }

            public void setInterest(String interest) {
                this.interest = interest;
            }

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
                this.code = code;
            }

            public Object getOffice() {
                return office;
            }

            public void setOffice(Object office) {
                this.office = office;
            }

            public int getCheck_state() {
                return check_state;
            }

            public void setCheck_state(int check_state) {
                this.check_state = check_state;
            }
        }
    }
}

