import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private PrivateKey privKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setBackground(new Color(192, 192, 192));
		setTitle("Ứng dụng chữ ký số");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 722, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("File");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 135, 56, 32);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(105, 130, 272, 47);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Open File");
		btnNewButton.addActionListener(new ActionListener() {
			private File file;

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int status = fileChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					textField.setText(file.getAbsolutePath());
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(409, 137, 87, 21);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Ký");
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					/* Generate a key pair */
					KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
					SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
					keyGen.initialize(1024, random);
					KeyPair pair = keyGen.generateKeyPair();
					PrivateKey priv = pair.getPrivate();
					PublicKey pub = pair.getPublic();
					/* Create a Signature object and initialize it with the private key */
					Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
					dsa.initSign(priv);
					/* Update and sign the data */
					FileInputStream fis = new FileInputStream("C:\\Users\\User\\OneDrive\\Desktop\\Hoá Đơn.pdf");
					BufferedInputStream bufin = new BufferedInputStream(fis);
					byte[] buffer = new byte[1024];
					int len;
					while (bufin.available() != 0) {
						len = bufin.read(buffer);
						dsa.update(buffer, 0, len);
					}
					;
					bufin.close();
					/*
					 * Now that all the data to be signed has been read in, generate a signature for
					 * it
					 */
					byte[] realSig = dsa.sign();
					/* Save the signature in a file */
					FileOutputStream sigfos = new FileOutputStream("C:\\Users\\User\\OneDrive\\Desktop\\HD.pdf");
					sigfos.write(realSig);
					sigfos.close();
					/* Save the public key in a file */
					byte[] key = pub.getEncoded();
					FileOutputStream keyfos = new FileOutputStream("C:\\Users\\\\User\\OneDrive\\Desktop\\pubKey.txt");
					keyfos.write(key);
					keyfos.close();
				} catch (Exception e2) {
					System.err.println("Caught exception " + e2.toString());
				}
			}
		});

		btnNewButton_1.setBounds(105, 224, 85, 21);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btnNewButton_2) {
					System.exit(0);
				}
			}
		});
		btnNewButton_2.setBackground(new Color(192, 192, 192));
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton_2.setBounds(258, 224, 85, 21);
		contentPane.add(btnNewButton_2);
	}

}
