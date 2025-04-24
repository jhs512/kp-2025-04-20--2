CREATE TABLE IF NOT EXISTS post_2gram
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    title    TEXT NOT NULL,
    content     LONGTEXT NOT NULL,
    FULLTEXT INDEX idx_ft_title (title) COMMENT 'parser "TokenBigram"',
    FULLTEXT INDEX idx_ft_content (content) COMMENT 'parser "TokenBigram"'
) ENGINE=Mroonga DEFAULT CHARSET=utf8mb4 COMMENT='engine "InnoDB"';
