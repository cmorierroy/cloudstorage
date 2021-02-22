package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper
{
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userId}")
    Credential getCredentialForUserIdById(Integer credentialid, int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getALlCredentialsForUserId(int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url} AND userid = #{userId}")
    Credential getCredentialForUserIdByUrl(String url, int userId);

    @Select("SELECT key FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userId}")
    String getKeyForCredentialById(Integer credentialid, Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, password,  key, userid) VALUES(#{url}, #{userName}, #{password}, #{key}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, password = #{password}, key = #{key} WHERE credentialid = #{credentialid}")
    int updateCredentialById(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid} AND userid = #{userId}")
    int delete(int credentialid, int userId);
}
