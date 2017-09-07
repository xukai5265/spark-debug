package cn.xukai.spark.jdbc;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;


public abstract class IdEntity {
	
	protected Long id;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	protected Date createtime = new Date();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getCreatetime() {
		return createtime;
	}
	
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
