package S_DES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

//普通加密
class S_Des_en extends JFrame {

    // GUI组件
    private JTextField keyField;
    private JTextField inputField;
    private JTextField outputField;
    private JButton encryptButton;
    private JButton backButton;

    private JButton generateKeyButton;

    public S_Des_en() {
        // 设置窗口标题
        setTitle("S-DES 加解密工具");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        setLayout(new GridLayout(6, 2));

        // 创建并添加组件
        add(new JLabel("密钥 (10位二进制):"));
        keyField = new JTextField();
        add(keyField);

        add(new JLabel("输入明文 (8位二进制):"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel("输出结果:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        encryptButton = new JButton("加密");
        backButton = new JButton("返回");
        generateKeyButton = new JButton("随机生成密钥");

        add(encryptButton);
        add(generateKeyButton);
        add(backButton);

        // 加密按钮事件监听
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String plaintext = inputField.getText();
                if (validateInput(key, plaintext, 10, 8)) {
                    String[] keys = methods.generateKeys(key);
                    String ciphertext = methods.encrypt(plaintext, keys);
                    outputField.setText(ciphertext);
                } else {
                    JOptionPane.showMessageDialog(S_Des_en.this, "请输入正确的10位密钥和8位明文。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(10);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(S_Des_en.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_DesMainpage().setVisible(true);
                dispose();
               }
        });

    }

    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }
}

//解密
class S_Des_de extends JFrame {

    // GUI组件
    private JTextField keyField;
    private JTextField inputField;
    private JTextField outputField;
    private JButton backButton;
    private JButton decryptButton;
    private JButton generateKeyButton;

    public S_Des_de() {
        // 设置窗口标题
        setTitle("S-DES 加解密工具");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        setLayout(new GridLayout(6, 2));

        // 创建并添加组件
        add(new JLabel("密钥 (10位二进制):"));
        keyField = new JTextField();
        add(keyField);

        add(new JLabel("输入密文 (8位二进制):"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel("输出结果:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);


        decryptButton = new JButton("解密");
        generateKeyButton = new JButton("随机生成密钥");
        backButton = new JButton("返回");

        add(decryptButton);
        add(generateKeyButton);
        add(backButton);

        // 返回按钮事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_DesMainpage().setVisible(true);
                dispose();
            }
        });

        //解密按钮
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String ciphertext = inputField.getText();
                if (validateInput(key, ciphertext, 10, 8)) {
                    String[] keys = methods.generateKeys(key);
                    String plaintext = methods.decrypt(ciphertext, keys);
                    outputField.setText(plaintext);
                } else {
                    JOptionPane.showMessageDialog(S_Des_de.this, "请输入正确的10位密钥和8位密文。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(10);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(S_Des_de.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }
}

//暴力破解功能
class S_Des_decode extends JFrame {
    private JButton crackButton;
    private JButton backButton;
    private JTextField plaintextField;
    private JTextField ciphertextField;
    private JTextArea resultArea;

    public S_Des_decode() {
        // 设置窗口标题和大小
        setTitle("S-DES 密钥破解工具");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 设置间距

        // 添加明文标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // 左对齐
        add(new JLabel("明文(8位二进制):"), gbc);

        gbc.gridx = 1;
        plaintextField = new JTextField(10);
        add(plaintextField, gbc);

        // 添加密文标签和输入框
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("密文(8位二进制):"), gbc);

        gbc.gridx = 1;
        ciphertextField = new JTextField(10);
        add(ciphertextField, gbc);

        // 添加密钥结果区域
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        resultArea = new JTextArea(5, 30); // 设置文本区域大小
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), gbc); // 使用滚动面板

        // 创建按钮并设置位置
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        crackButton = new JButton("破解密钥");
        add(crackButton, gbc);

        gbc.gridx = 1;
        backButton = new JButton("返回");
        add(backButton, gbc);
        // 破解密钥按钮事件监听
        crackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plaintext = plaintextField.getText();
                String ciphertext = ciphertextField.getText();
                resultArea.setText(""); // 清空结果区域
                // 遍历密钥空间进行破解
                for (String possibleKey : generateKeySpace()) {
                    // 使用给定的密钥进行解密
                    String decryptedText = methods.decrypt(ciphertext, methods.generateKeys(possibleKey));
                    // 如果解密结果与明文匹配，输出该密钥
                    if (decryptedText.equals(plaintext)) {
                        resultArea.append("可能的密钥: " + possibleKey + "\n");
                    }
                }
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_DesMainpage().setVisible(true);
                dispose();
            }
        });

    }

    // 生成密钥空间
    private String[] generateKeySpace() {
        String[] keys = new String[1024]; // 10 位二进制密钥总共 1024 种可能
        for (int i = 0; i < 1024; i++) {
            keys[i] = String.format("%10s", Integer.toBinaryString(i)).replace(' ', '0');
        }
        return keys;
    }
}

class S_Des_ASCII extends JFrame {
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton backButton;

    public S_Des_ASCII() {
        // 设置窗口标题
        setTitle("S-DES 加解密工具（ASCII）");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局为 GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 创建按钮
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        backButton = new JButton("返回");

        // 设置按钮位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置按钮之间的间距
        gbc.anchor = GridBagConstraints.CENTER; // 使按钮居中
        add(encryptButton, gbc);

        gbc.gridy = 1;
        add(decryptButton, gbc);

        gbc.gridy = 2;
        add(backButton, gbc);

        // 加密按钮事件监听
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_ASCII_en().setVisible(true);
                dispose();
            }
        });

        //解密按钮
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_ASCII_de().setVisible(true);
                dispose();
            }
        });

        // 返回按钮的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_DesMainpage().setVisible(true);
                dispose();

            }
        });

    }
}

//ASCII码输入加密
class S_Des_ASCII_en extends JFrame {
    // GUI组件
    private JTextField keyField;
    private JTextField inputField;
    private JTextField outputField;
    private JButton encryptButton;
    private JButton backButton;

    private JButton generateKeyButton;

    public S_Des_ASCII_en() {
        // 设置窗口标题
        setTitle("S-DES 加解密工具（ASCII）");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        setLayout(new GridLayout(6, 2));

        // 创建并添加组件
        add(new JLabel("密钥 (10位二进制):"));
        keyField = new JTextField();
        add(keyField);

        add(new JLabel("输入明文 (1 byte):"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel("输出结果:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);

        encryptButton = new JButton("加密");
        backButton = new JButton("返回");
        generateKeyButton = new JButton("随机生成密钥");

        add(encryptButton);
        add(generateKeyButton);
        add(backButton);

        // 按钮事件监听
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String plaintext = inputField.getText();

                // 将输入字符转换为8位二进制字符串
                if (plaintext.length() == 1) {
                    char character = plaintext.charAt(0); // 获取输入的单个字符
                    String binaryPlaintext = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0'); // 转换为8位二进制字符串
                    plaintext = binaryPlaintext;
                }
                if (validateInput(key, plaintext, 10, 8)) {
                    String[] keys = methods.generateKeys(key);
                    String ciphertext_0 = methods.encrypt(plaintext, keys);
                    int decimalValue = Integer.parseInt(ciphertext_0, 2);
                    //转换为对应的 ASCII 字符并返回为字符串
                    String ciphertext = Character.toString((char) decimalValue);
                    outputField.setText(ciphertext);
                } else {
                    JOptionPane.showMessageDialog(S_Des_ASCII_en.this, "请输入正确的10位密钥和8位明文。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(10);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(S_Des_ASCII_en.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 返回的事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_ASCII().setVisible(true);
                dispose();
            }
        });

    }

    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }
}

//ASCII码解密
class S_Des_ASCII_de extends JFrame {

    // GUI组件
    private JTextField keyField;
    private JTextField inputField;
    private JTextField outputField;
    private JButton backButton;
    private JButton decryptButton;
    private JButton generateKeyButton;

    public S_Des_ASCII_de() {
        // 设置窗口标题
        setTitle("S-DES 加解密工具（ASCII）");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        setLayout(new GridLayout(6, 2));

        // 创建并添加组件
        add(new JLabel("密钥 (10位二进制):"));
        keyField = new JTextField();
        add(keyField);

        add(new JLabel("输入密文 (1 byte):"));
        inputField = new JTextField();
        add(inputField);

        add(new JLabel("输出结果:"));
        outputField = new JTextField();
        outputField.setEditable(false);
        add(outputField);


        decryptButton = new JButton("解密");
        generateKeyButton = new JButton("随机生成密钥");
        backButton = new JButton("返回");

        add(decryptButton);
        add(generateKeyButton);
        add(backButton);

        // 按钮事件监听
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_ASCII().setVisible(true);
                dispose();
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = keyField.getText();
                String ciphertext = inputField.getText();

                // 确保输入的密文为 1 个字符，并将其转换为 8 位二进制字符串
                if (ciphertext.length() == 1) {
                    char character = ciphertext.charAt(0); // 获取输入的单个字符
                    String binaryCiphertext = String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0'); // 转换为8位二进制字符串
                    ciphertext = binaryCiphertext;
                }

                if (validateInput(key, ciphertext, 10, 8)) {
                    String[] keys = methods.generateKeys(key);
                    String binaryPlaintext = methods.decrypt(ciphertext, keys);

                    // 将解密后的二进制字符串转换为对应的明文字符
                    int decimalValue = Integer.parseInt(binaryPlaintext, 2);
                    String plaintext = Character.toString((char) decimalValue);

                    outputField.setText(plaintext);
                } else {
                    JOptionPane.showMessageDialog(S_Des_ASCII_de.this, "请输入正确的10位密钥和8位密文。", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // 随机生成密钥按钮的事件监听
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String randomKey = generateRandomKey(10);
                keyField.setText(randomKey);
                JOptionPane.showMessageDialog(S_Des_ASCII_de.this, "生成的随机密钥: " + randomKey, "随机密钥生成", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    // 验证输入是否是二进制且长度正确
    private boolean validateInput(String key, String text, int keyLength, int textLength) {
        return key.matches("[01]{" + keyLength + "}") && text.matches("[01]{" + textLength + "}");
    }

    // 生成随机的二进制密钥
    private String generateRandomKey(int length) {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(random.nextInt(2)); // 生成0或1
        }
        return key.toString();
    }
}

//主页面
public class S_DesMainpage extends JFrame {
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton decodeButton;
    private JButton ASCIIButton;

    public S_DesMainpage() {
        // 设置窗口标题
        setTitle("S-DES 加解密工具");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置布局
        // 设置布局为 GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 创建按钮
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        ASCIIButton = new JButton("ASCII加解密");
        decodeButton = new JButton("破解");

        // 设置按钮位置
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置按钮之间的间距
        gbc.anchor = GridBagConstraints.CENTER; // 使按钮居中
        add(encryptButton, gbc);

        gbc.gridy = 1;
        add(decryptButton, gbc);

        gbc.gridy = 2;
        add(ASCIIButton, gbc);

        gbc.gridy = 3;
        add(decodeButton, gbc);



        // 加密按钮事件监听
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_en().setVisible(true);
                dispose();
            }
        });

        //解密
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_de().setVisible(true);
                dispose();
            }
        });

        // 破解按钮的事件监听
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_decode().setVisible(true);
                dispose();

            }
        });

        // ascII加解密按钮的事件监听
        ASCIIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new S_Des_ASCII().setVisible(true);
                dispose();

            }
        });

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new S_DesMainpage().setVisible(true);
            }
        });
    }
}
