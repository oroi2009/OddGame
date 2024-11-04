import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class OddGame extends JFrame{
	public OddGame() {
		super("홀짝 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,250);
		setContentPane(new MyPanel());
		setVisible(true);
	}
	class MyPanel extends JPanel{
		Random random = new Random();
		private int answer = random.nextInt(10) + 1;
		private JLabel label = new JLabel("?");
		private JLabel message = new JLabel("무엇일까요 ?");
		private JButton [] btns = {
				new JButton("홀"),
				new JButton("짝"),
				new JButton("확인"),
				new JButton("다시")
		};
		
		private MyPanel() {
			setLayout(null);
			label.setFont(new Font("고딕",Font.PLAIN,30));
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setSize(80,80);
			label.setLocation(100,30);
			label.setBackground(Color.magenta);
			label.setOpaque(true);
			add(label); //this 생략
			message.setSize(150, 50);
			message.setLocation(65,120);
			message.setHorizontalAlignment(JLabel.CENTER);
			add(message);
			//myaction에 mypanel 인스턴스 전달?
			MyAction myaction = new MyAction(this);
			
			for(int i=0; i<btns.length; i++) {
				JButton b = btns[i];
				b.setSize(60,30);
				b.setLocation(10+i*60,160);
				b.addActionListener(myaction);
				add(b);
			}
		}
		//변경하기 위한 메소드
		public void set_panel_message(String la, String msg) {
			label.setText(la);
			message.setText(msg);
		}
		
		public void set_answer(int num) {
			answer = num;
		}
	}
	class MyAction implements ActionListener{
		private String user;
		private MyPanel panel; //Mypanel에 접근하기 위한 참조
		private int retry=0;
		
		public MyAction(MyPanel panel) {
			this.panel = panel;
		}
		
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			switch(b.getText()) {
			case "홀":
				user = "홀";
				break;
			case "짝":
				user = "짝";
				break;
			case "확인":
				check(); user = "";
				break;
			case "다시":
				refrash(); user = "";
				break;
			}
		}
		private void check() {
			if(user==null || user.isEmpty())
				panel.set_panel_message("?","홀이나 짝 먼저 선택!");
			else {
				if(retry>0)
					panel.set_panel_message("?","다시 버튼을 눌러주세요!");
				else {
					if(panel.answer%2==0){
						if(user =="짝")
							panel.set_panel_message(Integer.toString(panel.answer),"짝! 맞았어요.");
						else
							panel.set_panel_message(Integer.toString(panel.answer), "짝! 아쉽군요");
					}
					else {
						if(user =="홀")
							panel.set_panel_message(Integer.toString(panel.answer),"홀! 맞았어요.");
						else
							panel.set_panel_message(Integer.toString(panel.answer), "홀! 아쉽군요");
					}
				}
				retry++;
			}
		}
		private void refrash() {
			retry = 0;
			panel.set_answer(panel.random.nextInt(10) + 1);
			panel.set_panel_message("?","무엇일까요?");
		}
		//다시 버튼을 누르지 않으면 값이 변하지 않아사거 계속 게임이 진행되고 있음
	}
	public static void main(String[] args) {
		new OddGame();
	}
}
