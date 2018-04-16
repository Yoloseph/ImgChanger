public class Main{
	static ConversaoRGB con = new ConversaoRGB();
	static GaussFilter filter = new GaussFilter();
	public static void main(String[] args){
		con.starter();
		filter.run();
	}

}
