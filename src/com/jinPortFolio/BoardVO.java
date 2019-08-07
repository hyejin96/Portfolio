package com.jinPortFolio;

public class BoardVO {
	private int boardId;
	private String title;
	private String content;
	private String write;
	private String file;
	private String writeDate;
	private String updateDate;
	private String boardHit;
	
	public BoardVO(){}

	public BoardVO(int boardId, String title, String content, String write, String file, String writeDate,
			String updateDate, String boardHit) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.write = write;
		this.file = file;
		this.writeDate = writeDate;
		this.updateDate = updateDate;
		this.boardHit = boardHit;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWrite() {
		return write;
	}

	public void setWrite(String write) {
		this.write = write;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getBoardHit() {
		return boardHit;
	}

	public void setBoardHit(String boardHit) {
		this.boardHit = boardHit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardHit == null) ? 0 : boardHit.hashCode());
		result = prime * result + boardId;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result + ((write == null) ? 0 : write.hashCode());
		result = prime * result + ((writeDate == null) ? 0 : writeDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardVO other = (BoardVO) obj;
		if (boardHit == null) {
			if (other.boardHit != null)
				return false;
		} else if (!boardHit.equals(other.boardHit))
			return false;
		if (boardId != other.boardId)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else if (!updateDate.equals(other.updateDate))
			return false;
		if (write == null) {
			if (other.write != null)
				return false;
		} else if (!write.equals(other.write))
			return false;
		if (writeDate == null) {
			if (other.writeDate != null)
				return false;
		} else if (!writeDate.equals(other.writeDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoardVO [boardId=" + boardId + ", title=" + title + ", content=" + content + ", write=" + write
				+ ", file=" + file + ", writeDate=" + writeDate + ", updateDate=" + updateDate + ", boardHit="
				+ boardHit + "]";
	}

		
}
