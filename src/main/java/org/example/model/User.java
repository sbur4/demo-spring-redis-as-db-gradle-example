package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("User")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private  String firstName;
	private  String lastName;
}