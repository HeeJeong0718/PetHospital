package com.example.PetHospital.config;

import java.util.Map;

import com.example.PetHospital.domain.Role;
import com.example.PetHospital.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes {
	
	  private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
	    private String nameAttributeKey;
	    private String name;
	    private String email;
	   // private String picture;
	    
	    

	    @Builder
	    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
	        this.attributes = attributes;
	        this.nameAttributeKey = nameAttributeKey;
	        this.name = name;
	        this.email = email;
	      //  this.picture = picture;
	    }

	    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
	        // 여기서 네이버와 카카오 등 구분 (ofNaver, ofKakao)
            //naver
	    	if("naver".equals(registrationId)) {
	    		return ofNaver("id", attributes);
	    	}
	    	//google
	        return ofGoogle(userNameAttributeName, attributes);
	    }
	    //naver
	    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
	       //json 형태라서 Map을 통해서 데이터를가져온다
	    	@SuppressWarnings("unchecked")
	    	  Map<String, Object> response = (Map<String, Object>) attributes.get("response");
	    	 return OAuthAttributes.builder()
	    			  .name((String) response.get("name"))
	                  .email((String) response.get("email"))
	                 // .picture((String) response.get("profile_image"))
	                  .attributes(response)
	                  .nameAttributeKey(userNameAttributeName)
	                  .build();
	    			 
	    }

	    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
	        return OAuthAttributes.builder()
	                .name((String) attributes.get("username"))
	                .email((String) attributes.get("email"))
	               // .picture((String) attributes.get("picture"))
	                .attributes(attributes)
	                .nameAttributeKey(userNameAttributeName)
	                .build();
	    }

	    public Users toEntity(){
	        return Users.builder()
	                .name(name)
	                .email(email)
	               // .picture(picture)
	                .role(Role.USER) // 기본 권한 GUEST
	                .build();
	    }

}
