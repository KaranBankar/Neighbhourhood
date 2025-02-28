package com.example.neighbourhood;

public class Member {
    String memberId, name, email, mobile, password;

    public Member() {
        // Default constructor required for Firebase
    }

    public Member(String memberId, String name, String email, String mobile, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getPassword() { return password; }
}

