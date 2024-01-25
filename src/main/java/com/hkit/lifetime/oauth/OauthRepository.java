package com.hkit.lifetime.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthRepository extends JpaRepository<Oauth, String> {
}