package hajiboot;

public class Main {
	public static void main(String[] args) {
		MarkdownRenderer markdownRenderer = new GitHubApiMarkdownRenderer();
		MarkdownPrinter markdownPrinter = new MarkdownPrinter(markdownRenderer);
		markdownPrinter.print(System.in);
	}
}
