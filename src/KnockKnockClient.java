import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.DropMode;

public class KnockKnockClient extends JFrame {

	private JPanel contentPane;
	private JTextField TFIP;
	private JTextField TFP;
	private JTextField TFMSG;
	Socket sock = null;
	PrintWriter writeSock;
	BufferedReader readSock;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KnockKnockClient frame = new KnockKnockClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public KnockKnockClient() {
		setTitle("Knock Knock Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextArea TAIP = new JTextArea();
		TAIP.setEditable(false);
		TAIP.setEnabled(false);
		TAIP.setText("IP Address:");
		
		TFIP = new JTextField();
		TFIP.setText("constance.cs.rutgers.edu");
		TFIP.setColumns(10);
		
		JTextArea TAP = new JTextArea();
		TAP.setEditable(false);
		TAP.setEnabled(false);
		TAP.setText("Port Number:");
		
		TFP = new JTextField();
		TFP.setText("5520");
		TFP.setColumns(10);
		
		JTextArea TAMSG = new JTextArea();
		TAMSG.setEditable(false);
		TAMSG.setEnabled(false);
		TAMSG.setText("Message to Server");
		
		TFMSG = new JTextField();
		TFMSG.setColumns(10);
		
		JTextArea TALOG = new JTextArea();
		TALOG.setEditable(false);
				
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(btnConnect.getText().equals("Connect")) {
						int portNum = Integer.parseInt(TFP.getText());
						String HostAddress = TFIP.getText();
						try {
							sock = new Socket(HostAddress, portNum);
							writeSock = new PrintWriter(sock.getOutputStream(), true);
							readSock = new BufferedReader(new InputStreamReader( sock.getInputStream()));
							btnConnect.setText("Disconnect");
							TALOG.append("Connected\n");
						} catch (Exception e) {
							TALOG.append("Error: "+ e + "\n");
							System.out.println(("Error: "+ e));
							sock = null;
							writeSock = null;
							readSock = null;
							btnConnect.setText("Connect");
							TALOG.append("Disconnected\n");
							e.printStackTrace();
						}
					}
					else {
						try {
							readSock.close();
							writeSock.close();
							sock.close();
							sock = null;
							btnConnect.setText("Connect");
							TALOG.append("Disconnected\n");
						} catch (Exception e) {
							TALOG.append("Error: "+ e + "\n");
							System.out.println("Error: "+ e);
							sock = null;
							writeSock = null;
							readSock = null;
							btnConnect.setText("Connect");
							TALOG.append("Disconnected\n");
							e.printStackTrace();
						}
					}
					
				}
			});
		
		
		JButton btnSend = new JButton("Send");
		
		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sock == null) {
					TALOG.append("Error: Not connected\n");
				}
				if(sock != null) {
					writeSock.println(TFMSG.getText());
					TALOG.append("Client: "+ TFMSG.getText() + "\n");
					String msg = null;
					try {
						msg = readSock.readLine();
					} catch (IOException e) {
						TALOG.append("Error: "+ e + "\n");
						System.out.println("Error: "+e.getMessage());
						e.printStackTrace();
					}
					if(msg == null) {
						try {
							readSock.close();
							writeSock.close();
							sock.close();
							sock = null;
							btnConnect.setText("Connect");
							TALOG.append("Disconnected\n");
						} catch (Exception e) {
							System.out.println("Error: "+ e.getMessage());
							sock = null;
							writeSock = null;
							readSock = null;
							btnConnect.setText("Connect");
							TALOG.append("Disconnected\n");
							e.printStackTrace();
						}
					} else {
						TALOG.append("Server: "+ msg + "\n");
						if(msg.equals("Good Bye!")) {
							try {
								readSock.close();
								writeSock.close();
								sock.close();
								sock = null;
								btnConnect.setText("Connect");
								TALOG.append("Disconnected\n");
							} catch (Exception e) {
								System.out.println("Error: "+ e.getMessage());
								sock = null;
								writeSock = null;
								readSock = null;
								btnConnect.setText("Connect");
								TALOG.append("Disconnected\n");
								e.printStackTrace();
							}
						}
					}
				}
				
			}
			
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(TAP, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(TAIP, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(TFP)
										.addComponent(TFIP, GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(TAMSG, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSend)))
							.addContainerGap(91, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(TALOG, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
								.addComponent(TFMSG, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(TFIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(TAIP, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(TAP, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(TFP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConnect)
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(TAMSG, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSend))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(TFMSG, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(TALOG, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
