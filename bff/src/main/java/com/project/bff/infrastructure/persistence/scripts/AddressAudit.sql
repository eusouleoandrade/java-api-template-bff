use bffdb

CREATE TABLE addressAudit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cep VARCHAR(8) NOT NULL,
    dataHora DATETIME NOT NULL,
    PRIMARY KEY (id)
);