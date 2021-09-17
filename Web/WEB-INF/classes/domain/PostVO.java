package domain;

public class PostVO {
	private int postNum;
	private int boardId;
	private String title;
	private String id;
	private String content;
	private int view;
	private String date;

	public int getPostNum() {
		return this.postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public int getBoardId() {
		return this.boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getView() {
		return this.view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}