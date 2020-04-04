package net.paramount.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.paramount.framework.entity.RepoAuditable;

/**
 * An attachment.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attachment")
public class Attachment extends RepoAuditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5803112544828198887L;

	@Setter
	@Getter
	@Column(name = "name", nullable = false, length=200)
	private String name;

	@Setter
	@Getter
  @Column(name = "mimetype", length=200)
  private String mimetype;
  
	@Setter
	@Getter
	@Lob
  private byte[] data;

	@Setter
	@Getter
	@Column(name = "encryption_key", length=200)
	private String encryptionKey;

}
