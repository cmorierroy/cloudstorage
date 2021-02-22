package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService
{
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService)
    {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential, int userId) throws IOException
    {
        //if url is found, it already exists
        if(getCredentialForUserIdById(credential.getCredentialid(), userId) != null)
        {
            credential.setUrl(credential.getUrl());
            credential.setUserName(credential.getUserName());

            String key = credentialMapper.getKeyForCredentialById(credential.getCredentialid(), userId);
            String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);

            credential.setPassword(encryptedPassword);
            credential.setKey(key);
            return credentialMapper.updateCredentialById(credential);
        }
        else //create new Credential!
        {
            Credential newCred = new Credential();

            newCred.setUrl(credential.getUrl());
            newCred.setPassword(credential.getPassword());
            newCred.setUserName(credential.getUserName());
            newCred.setUserId(userId);

            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            String key = Base64.getEncoder().encodeToString(salt);

            newCred.setKey(key);
            newCred.setPassword(encryptionService.encryptValue(credential.getPassword(), key));

            return credentialMapper.insert(newCred);
        }
    }

    public int deleteCredential(int id, int userId)
    {
        return credentialMapper.delete(id, userId);
    }

    public List<Credential> getAllCredentialsForUserId(int userId)
    {
        return credentialMapper.getALlCredentialsForUserId(userId);
    }

    public Credential getCredentialForUserIdById(Integer credentialid, int userId)
    {
        return credentialMapper.getCredentialForUserIdById(credentialid, userId);
    }
}
