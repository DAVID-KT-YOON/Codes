CREATE TABLE conversations (
  id CHAR(36) PRIMARY KEY
);

CREATE TABLE messages (
  id BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  conversation_id CHAR(36) NOT NULL,
  response_id VARCHAR(80) UNIQUE NULL,    
  sender ENUM('user', 'bot') NOT NULL,  
  content MEDIUMTEXT NOT NULL,
  created_at DATETIME(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  FOREIGN KEY (conversation_id) REFERENCES conversations(id)
);
