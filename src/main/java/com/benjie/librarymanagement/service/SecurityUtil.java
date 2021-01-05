package com.benjie.librarymanagement.service;

/*
 * Created by OPARA BENJAMIN
 * On 1/5/2021 - 3:44 PM
 */

import com.benjie.librarymanagement.entity.LibraryMember;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SecurityUtil {

    private SecretKey securityKey;
    public static final String HASH_PASSWORD_KEY = "hashedPassword";
    public static final String SALT_KEY = "salt";
    public static final String BEARER = "bearer";

    @Inject
    private MemberQueryService memberQueryService;

    @PostConstruct
    public void init() {
        securityKey = generateKey();
    }

    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public boolean passwordMatch(String dbStoredHashedPassword, String saltText, String clearTextPassword) {
        ByteSource salt = ByteSource.Util.bytes(Hex.decode(saltText));
        String hashedPassword = hashAndSaltPassword(clearTextPassword, salt);
        return hashedPassword.equals(dbStoredHashedPassword);
    }

    public Map<String, String> hashPassword(String clearTextPassword) {
        ByteSource salt = getSalt();

        Map<String, String> credMap = new HashMap<>();
        credMap.put(HASH_PASSWORD_KEY, hashAndSaltPassword(clearTextPassword, salt));
        credMap.put(SALT_KEY, salt.toHex());

        return credMap;
    }

    private String hashAndSaltPassword(String clearTextPassword, ByteSource salt) {
        return new Sha512Hash(clearTextPassword, salt, 2000000).toHex();
    }

    private ByteSource getSalt() {
        return new SecureRandomNumberGenerator().nextBytes();
    }

    public boolean authenticateUser(String email, String password) {
        LibraryMember member = memberQueryService.findMemberByEmail(email);
        if (member == null) {
            return false;
        }
        return passwordMatch(member.getPassword(), member.getPasswordSalt(), password);
    }

    private SecretKey generateKey() {
        return MacProvider.generateKey(SignatureAlgorithm.HS512);
    }

    public SecretKey getSecurityKey() {
        return securityKey;
    }

}
