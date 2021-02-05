package protocol;

public class Unpacker {

	private String input;

	public Unpacker(String input) {
		this.input = input;
	}

	public String getNextItem(int leftOffset, int rightOffset) {
		if (input.equals(";"))
			return null;

		int start = input.indexOf("=");
		if (start == -1)
			return null;
		start += leftOffset + 1;

		int end = input.indexOf(",");
		if (end == -1)
			end = input.indexOf(";");
		if (end == -1)
			return null;
		end -= rightOffset;

		String item = input.substring(start, end);
		int endOfFile = end + rightOffset + 1;
		if (endOfFile == input.length())
			endOfFile--;

		input = input.substring(endOfFile);
		return item.trim();
	}

}
