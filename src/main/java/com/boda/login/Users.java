package com.boda.login;

import java.util.ArrayList;

public class Users {

        private String username;
        private String password;

        public Users(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getter 方法
        public String getUsername() { return username; }
        public String getPassword() { return password; }

        // Setter 方法
        public void setUsername(String username) { this.username = username; }
        public void setPassword(String password) { this.password = password; }
    }
