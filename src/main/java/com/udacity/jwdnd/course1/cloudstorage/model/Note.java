package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note
{
    private Integer id;
    private String title;
    private String description;
    private int userId;

    public Note(Integer id, String title, String description)
    {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
}
