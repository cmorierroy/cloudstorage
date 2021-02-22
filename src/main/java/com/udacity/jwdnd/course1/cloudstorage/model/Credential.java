package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential
{
    private Integer credentialid;
    private String url;
    private String userName;
    private String key;
    private String password;
    private int userId;

    public Credential()
    {
        //leave empty
    }
    public Credential(Integer credentialid, String url, String userName, String key, String password, int userId)
    {
        this.credentialid = credentialid;
        this.url = url;
        this.userName = userName;
        this.key = key;
        this.password = password;
        this.userId = userId;

        System.out.println("IN CONSTRUCTOR: " + this.password);
        System.out.println("key: " + this.key);
    }

    public Integer getCredentialid()
    {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid)
    {
        this.credentialid = credentialid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
}
