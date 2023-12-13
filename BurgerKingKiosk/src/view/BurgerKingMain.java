package view;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import java.awt.Font;
import javax.swing.SwingConstants;

import javax.swing.JScrollPane;

import java.awt.GridLayout;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import jdbc.MysqlJdbc;
import model.dao.AdminDAO;
import model.dao.BurgerIngredientDAO;
import model.dao.MenuDAO;
import model.dto.MenuByTypeDTO;
import model.dto.OrderMenuDTO;
import model.vo.BurgerIngredientVO;
import model.vo.MenuTypeVO;
import model.vo.MenuVO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

public class BurgerKingMain extends JFrame {
	private int totalPrice = 0;
	private int count = 0;
	private List<MenuByTypeDTO> menuByTypeDTOs;	// 타입의 정보와 타입에 따른 메뉴 리스트를 가져오는 MenuByDTO List
	private List<RoundedButton> typeButtons = new ArrayList<RoundedButton>();	// 메뉴 타입들을 선택할 수 있게 해주는 버튼
	private List<JPanel> menuByTypePanels = new ArrayList<JPanel>();	// 타입별 메뉴를 보여줄 패널
	private List<JScrollPane> scrollPanes = new ArrayList<JScrollPane>();	// menuByTypePanels에 사용될 스크롤팬
	private List<BurgerIngredientVO> burgerIngredientVOs = new ArrayList<BurgerIngredientVO>();	// 모든 재료의 정보를 담을 List
	private int type_index = 0;	// 어떤 메뉴 타입 패널이 선택되었는지 구분하는 변수
	private int menubytype_index = 0;	// 어떤 타입에 따른 메뉴가 선택되었는지 구분하는 변수
	
	// 메뉴를 담을 장바구니
	ArrayList<OrderMenuDTO> basket = new ArrayList<OrderMenuDTO>();
	
	private ImageIcon checked;
	private ImageIcon unchecked;

	private JFrame frmBurgerkingKiosk;
	
	// for 메뉴 선택 화면
	private JPanel buttonPanel;
	private JPanel footerPanel;
	private JPanel totalPanel;

	// for AdminPWManager
	private JPasswordField setPW1;
	private JPasswordField setPW2;
	private String pw = "";
	private String pw2 = "";
	private JPanel AdminPWPanel;
	private JPanel AdminSuccessPanel;
	private JPanel totalPricePanel;
	
	//for LogIn
	private JPanel loginPanel;
	private JPasswordField enterPW;
	private String pw3 = "";

	//for UserStartManager
	private JPanel UserStartManagerPanel;
	
	// for burgerCompositionPanel
	private JPanel burgerCompositionPanel;
	JRadioButton burgerCompositionJb[] = new JRadioButton[2];
	ButtonGroup compositionBg = new ButtonGroup();
	JLabel lblNewLabel = new JLabel("");
	JLabel lblNewLabel2 = new JLabel("");
	
	//for burgerMenuCompositionPanel
	private JLabel ingredientLabel;
	private RoundedButton changeIngredientBtn;
	private JLabel sideLabel;
	private RoundedButton changeSideBtn;
	private JLabel drinkLabel;
	private RoundedButton changeDrinkBtn;
	private JPanel burgerMenuCompositionPanel;
	
	// for 재료 변경 Panel
	private JCheckBox ingredientjb[];
	private JPanel ingredientPanel;
	private JPanel ingredientListPanel;
	private JScrollPane ingredientScroll;
	private JPanel ingredientFooterPanel;
	
	// for 사이드 변경 Panel
	private ButtonGroup sidebg = new ButtonGroup();
	private JRadioButton sidejb[];
	private JPanel sidePanel;
	private JPanel sideListPanel;
	private JScrollPane sideScroll;
	private JPanel sideFooterPanel;
	
	// for 음료 변경 Panel
	ButtonGroup drinkbg = new ButtonGroup();
	JRadioButton drinkjb[];
	private JPanel drinkPanel;
	private JPanel drinkListPanel;
	private JScrollPane drinkScroll;
	private JPanel drinkFooterPanel;
	
	//for toGoPanel
	private JPanel toGoPanel;
	ButtonGroup bg3 = new ButtonGroup();
	JRadioButton whereToEatjb[] = new JRadioButton[2];
	private String whereToEat[] = {"매장 주문", "포장 주문"};
	
	//for orderCheckPanel
	private JScrollPane orderCheckScrollPane;
	private JPanel orderCheck;
	private JPanel orderPanel;
	private JPanel orderPanel1;
	private JPanel orderPanel2;
	private JPanel labelPanel;
	private JPanel labelFooter;
	
	public BurgerKingMain() {
		new MysqlJdbc();
		initialize();
	}

	private void initialize() {
		frmBurgerkingKiosk = new JFrame();
		frmBurgerkingKiosk.setTitle("BurgerKing Kiosk");
		frmBurgerkingKiosk.setBounds(100, 100, 312, 646);
		frmBurgerkingKiosk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBurgerkingKiosk.getContentPane().setLayout(null);
		frmBurgerkingKiosk.setLocationRelativeTo(null);
		
		// PW 저장하는 패널
		AdminPWPanel = new JPanel();
		AdminPWPanel.setBackground(new Color(255, 254, 244));
		AdminPWPanel.setBounds(0, 0, 312, 618);
		AdminPWPanel.setOpaque(true);
		frmBurgerkingKiosk.getContentPane().add(AdminPWPanel);
		AdminPWPanel.setLayout(null);
		
		//LogIn 패널
		loginPanel = new JPanel();
		loginPanel.setBackground(new Color(255, 253, 240));
		loginPanel.setBounds(0, 0, 312, 618);
		loginPanel.setOpaque(true);
		frmBurgerkingKiosk.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		loginPanel.setVisible(false);
		
		//AdminSuccess 패널
		AdminSuccessPanel = new JPanel();
		AdminSuccessPanel.setBackground(new Color(255, 253, 240));
		AdminSuccessPanel.setBounds(0, 0, 312, 618);
		frmBurgerkingKiosk.getContentPane().add(AdminSuccessPanel);
		AdminSuccessPanel.setLayout(null);
		AdminSuccessPanel.setVisible(false);
		
		//UserStartManager 패널
		UserStartManagerPanel = new JPanel();
		UserStartManagerPanel.setBackground(new Color(255, 253, 240));
		UserStartManagerPanel.setBounds(0, 0, 312, 618);
		frmBurgerkingKiosk.getContentPane().add(UserStartManagerPanel);
		UserStartManagerPanel.setLayout(null);
		UserStartManagerPanel.setVisible(false);

		// 버튼을 붙일 패널
		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(255, 253, 240));
		buttonPanel.setBounds(0, 0, 312, 99);
		buttonPanel.setLayout(new GridLayout(0, 4));
		buttonPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(buttonPanel);

		// 설정과 나가기 버튼을 붙일 footer 패널
		footerPanel = new JPanel();
		footerPanel.setOpaque(true);
		footerPanel.setLayout(null);
		footerPanel.setBounds(0, 578, 312, 40);
		footerPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(footerPanel);

		// 총 결제 정보를 알려주는 컴포넌트를 붙일 패널
		totalPanel = new JPanel();
		totalPanel.setLayout(null);
		totalPanel.setOpaque(true);
		totalPanel.setVisible(true);
		totalPanel.setBounds(0, 442, 312, 136);
		totalPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(totalPanel);

		// 버거 구성을 선택할 패널
		burgerCompositionPanel = new JPanel();
		burgerCompositionPanel.setBackground(new Color(255, 254, 240));
		burgerCompositionPanel.setBounds(0, 0, 312, 578);
		burgerCompositionPanel.setLayout(null);
		burgerCompositionPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(burgerCompositionPanel);
		
		// 버거 세트 선택 메뉴를 확인할 패널
		burgerMenuCompositionPanel = new JPanel();
		burgerMenuCompositionPanel.setBackground(new Color(255, 254, 240));
		burgerMenuCompositionPanel.setBounds(0, 0, 312, 578);
		burgerMenuCompositionPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(burgerMenuCompositionPanel);
		burgerMenuCompositionPanel.setLayout(null);
		
		// 재료 추가 패널들 구성
		ingredientPanel = new JPanel();
  		ingredientPanel.setBackground(new Color(255, 254, 240));
  		ingredientPanel.setBounds(0, 0, 312, 100);	// 578
  		ingredientPanel.setVisible(false);
  		ingredientPanel.setLayout(null);
  		frmBurgerkingKiosk.getContentPane().add(ingredientPanel);
  		
  		ingredientListPanel = new JPanel();
  		ingredientListPanel.setBackground(new Color(255, 254, 240));
  		ingredientListPanel.setPreferredSize(new Dimension(312, 400));
  		ingredientListPanel.setVisible(true);
  		ingredientListPanel.setLayout(new BoxLayout(ingredientListPanel, BoxLayout.Y_AXIS));
  		
        ingredientScroll = new JScrollPane(ingredientListPanel);
        ingredientScroll.setBorder(null);	// 테두리 없애기
        ingredientScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 막기
        ingredientScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 스크롤이 필요할 때만 나타나도록 설정
        frmBurgerkingKiosk.getContentPane().add(ingredientScroll);
        ingredientScroll.setBounds(0, 100, 312, 400);
        ingredientScroll.setVisible(false);
        
        ingredientFooterPanel = new JPanel();
        ingredientFooterPanel.setBackground(new Color(255, 254, 240));
        ingredientFooterPanel.setBounds(0, 500, 312, 78);
        ingredientFooterPanel.setLayout(null);
        ingredientFooterPanel.setVisible(false);
  		frmBurgerkingKiosk.getContentPane().add(ingredientFooterPanel);
		
		// 사이드 변경 패널들 구성
		sidePanel = new JPanel();
		sidePanel.setBackground(new Color(255, 254, 240));
		sidePanel.setBounds(0, 0, 312, 100);	// 578
		sidePanel.setVisible(false);
		sidePanel.setLayout(null);
		frmBurgerkingKiosk.getContentPane().add(sidePanel);
		
		sideListPanel = new JPanel();
		sideListPanel.setBackground(new Color(255, 254, 240));
		sideListPanel.setPreferredSize(new Dimension(312, 400));
		sideListPanel.setVisible(true);
		sideListPanel.setLayout(new BoxLayout(sideListPanel, BoxLayout.Y_AXIS));
		
		sideScroll = new JScrollPane(sideListPanel);
		sideScroll.setBorder(null);	// 테두리 없애기
		sideScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 막기
		sideScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 스크롤이 필요할 때만 나타나도록 설정
        frmBurgerkingKiosk.getContentPane().add(sideScroll);
        sideScroll.setBounds(0, 100, 312, 400);
        sideScroll.setVisible(false);
        
        sideFooterPanel = new JPanel();
        sideFooterPanel.setBackground(new Color(255, 254, 240));
        sideFooterPanel.setBounds(0, 500, 312, 78);
        sideFooterPanel.setLayout(null);
        sideFooterPanel.setVisible(false);
  		frmBurgerkingKiosk.getContentPane().add(sideFooterPanel);
		
		// 음료 추가 패널
  		drinkPanel = new JPanel();
  		drinkPanel.setBackground(new Color(255, 254, 240));
  		drinkPanel.setBounds(0, 0, 312, 100);	// 578
  		drinkPanel.setVisible(false);
  		drinkPanel.setLayout(null);
		frmBurgerkingKiosk.getContentPane().add(drinkPanel);
		
		drinkListPanel = new JPanel();
		drinkListPanel.setBackground(new Color(255, 254, 240));
		drinkListPanel.setPreferredSize(new Dimension(312, 400));
		drinkListPanel.setVisible(true);
		drinkListPanel.setLayout(new BoxLayout(drinkListPanel, BoxLayout.Y_AXIS));
		
		drinkScroll = new JScrollPane(drinkListPanel);
		drinkScroll.setBorder(null);	// 테두리 없애기
		drinkScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 막기
		drinkScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // 스크롤이 필요할 때만 나타나도록 설정
        frmBurgerkingKiosk.getContentPane().add(drinkScroll);
        drinkScroll.setBounds(0, 100, 312, 400);
        drinkScroll.setVisible(false);
        
        drinkFooterPanel = new JPanel();
        drinkFooterPanel.setBackground(new Color(255, 254, 240));
        drinkFooterPanel.setBounds(0, 500, 312, 78);
        drinkFooterPanel.setLayout(null);
        drinkFooterPanel.setVisible(false);
  		frmBurgerkingKiosk.getContentPane().add(drinkFooterPanel);
		
		//매장 혹은 포장 패널
		/*----------------toGoPanel = new JPanel();
		toGoPanel.setBackground(new Color(255, 254, 240));
		toGoPanel.setBounds(0, 0, 312, 578);
		toGoPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(toGoPanel);
		toGoPanel.setLayout(null);
		
		//주문확인 패널
		orderCheckScrollPane = new JScrollPane(orderCheck);
		orderCheckScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		orderCheckScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		orderCheckScrollPane.setBounds(0, 99, 312, 343);
		frmBurgerkingKiosk.getContentPane().add(orderCheckScrollPane, BorderLayout.CENTER);
		
		orderCheck = new JPanel();
		orderCheck.setBackground(new Color(255, 254, 244));
		orderCheck.setPreferredSize(new Dimension(312,1000));
		orderCheckScrollPane.setViewportView(orderCheck);
		orderCheck.setLayout(new BorderLayout());
		
		
		//주문확인 header
		labelPanel = new JPanel();
		labelPanel.setBackground(new Color(255, 254, 240));
		labelPanel.setBounds(0, 0, 312, 99);
		labelPanel.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(labelPanel);
		labelPanel.setLayout(null);
		
		//주문확인 footer
		labelFooter = new JPanel();
		labelFooter.setBackground(new Color(255, 254, 240));
		labelFooter.setBounds(0, 442, 312, 136);
		labelFooter.setVisible(false);
		frmBurgerkingKiosk.getContentPane().add(labelFooter);
		labelFooter.setLayout(null);------------------------------*/
		
/*---------------------------------------------------관리자 비밀번호 설정 여부 확인---------------------------------------------------------------*/
		
		if(AdminDAO.isAdminTableNotEmpty()) {	// 관리자 테이블에 계정이 존재 하면 관리자 로그인 화면
			AdminPWPanel.setVisible(false);
			loginPanel.setVisible(true);
		}
		else {	// 관리자 테이블에 계정이 존재하지 않으면 관리자 비밀번호 설정 화면
			AdminPWPanel.setVisible(true);
			loginPanel.setVisible(false);
		}
		
/*------------------------------------------------------ 관리자 패스워드 지정 Screen -----------------------------------------------------*/

		JLabel setPWLabel = new JLabel("패스워드를 지정하세요");
		setPWLabel.setForeground(new Color(87, 58, 52));
		setPWLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setPWLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		setPWLabel.setBounds(12, 125, 274, 50);
		AdminPWPanel.add(setPWLabel);

		setPW1 = new JPasswordField();
		setPW1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		setPW1.setColumns(10);
		setPW1.setBounds(20, 260, 258, 50);
		setPW1.setEchoChar('*');
		AdminPWPanel.add(setPW1);

		setPW2 = new JPasswordField();
		setPW2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		setPW2.setColumns(10);
		setPW2.setBounds(20, 365, 258, 50);
		setPW2.setEchoChar('*');
		AdminPWPanel.add(setPW2);

		JLabel lblNewLabel1 = new JLabel("다시 입력");
		lblNewLabel1.setForeground(new Color(87, 58, 52));
		lblNewLabel1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		lblNewLabel1.setBounds(20, 345, 70, 15);
		AdminPWPanel.add(lblNewLabel1);

		RoundedButton btn = new RoundedButton("확인");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pw = new String(setPW1.getPassword());
				pw2 = new String(setPW2.getPassword());

				if (pw.isEmpty() || pw2.isEmpty()) {
					JOptionPane.showMessageDialog(null, "password를 입력해주세요", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					if (pw.length() > 15 || pw2.length() > 15) {
						JOptionPane.showMessageDialog(null, "password는 1~15자 사이로 입력해주세요", "Message",
								JOptionPane.ERROR_MESSAGE);
					} else {

						if (!pw.equals(pw2))
							JOptionPane.showMessageDialog(null, "틀렸습니다.\n다시 입력해주세요.", "Message",
									JOptionPane.ERROR_MESSAGE);
						else {
							setPW1.setText("");
							setPW2.setText("");
							AdminDAO.insertAdmin(pw2);
							nextComposition(AdminPWPanel, AdminSuccessPanel);
							
						}
					}
				}

			}
		});
		btn.setForeground(new Color(255, 254, 244));
		btn.setBackground(new Color(87, 58, 52));
		btn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		btn.setBounds(20, 478, 258, 50);
		AdminPWPanel.add(btn);
		
/*------------------------------------------------------- Login Screen ----------------------------------------------------------------*/
		
		JLabel setLoginLabel = new JLabel("패스워드를 입력해주세요");
		setLoginLabel.setForeground(new Color(87, 58, 52));
		setLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setLoginLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		setLoginLabel.setBounds(12, 125, 274, 50);
		loginPanel.add(setLoginLabel);

		enterPW = new JPasswordField();
		enterPW.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		enterPW.setColumns(10);
		enterPW.setBounds(20, 280, 258, 50);
		enterPW.setEchoChar('*');
		loginPanel.add(enterPW);

		RoundedButton pwBtn = new RoundedButton("확인");
		pwBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pw3 = new String(enterPW.getPassword());

				if (pw3.isEmpty()) {
					JOptionPane.showMessageDialog(null, "password를 입력해주세요", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					if (pw3.length() > 15) {
						JOptionPane.showMessageDialog(null, "password는 1~15자 사이로 입력해주세요", "Message",
								JOptionPane.ERROR_MESSAGE);
					} else {

						if (!pw3.equals(AdminDAO.getAdminPassword(1))) {
							JOptionPane.showMessageDialog(null, "틀렸습니다.\n다시 입력해주세요.", "Message",
									JOptionPane.ERROR_MESSAGE);
							}
						else {
							nextComposition(loginPanel, AdminSuccessPanel);
							enterPW.setText("");
						}
					}
				}

			}
		});
		pwBtn.setForeground(new Color(255, 254, 244));
		pwBtn.setBackground(new Color(87, 58, 52));
		pwBtn.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		pwBtn.setBounds(20, 478, 258, 50);
		loginPanel.add(pwBtn);
		
/*------------------------------------------------------ 관리자 화면 ----------------------------------------------------------*/
		
		RoundedButton AdminSuccesstoStartPage = new RoundedButton("나가기");
		AdminSuccesstoStartPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminSuccessPanel.setVisible(false);
				loginPanel.setVisible(true);
			}
		});
		AdminSuccesstoStartPage.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		AdminSuccesstoStartPage.setHorizontalAlignment(SwingConstants.RIGHT);
		AdminSuccesstoStartPage.setForeground(new Color(255, 254, 240));
		AdminSuccesstoStartPage.setBackground(new Color(87, 58, 52));
		AdminSuccesstoStartPage.setBounds(220, 577, 86, 38);
		AdminSuccessPanel.add(AdminSuccesstoStartPage);
		
		
		RoundedButton price = new RoundedButton("매출 확인");
		price.setBackground(new Color(87, 58, 52));
		price.setForeground(new Color(255, 253, 240));
		price.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
		price.setBounds(43, 157, 220, 50);
		AdminSuccessPanel.add(price);
		
		RoundedButton startMenu = new RoundedButton("시작 화면으로 가기");
		startMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminSuccessPanel.setVisible(false);
				UserStartManagerPanel.setVisible(true);
			}
		});
		startMenu.setBackground(new Color(87, 58, 52));
		startMenu.setForeground(new Color(255, 253, 240));
		startMenu.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
		startMenu.setBounds(43, 246, 220, 50);
		AdminSuccessPanel.add(startMenu);
		
		JLabel AdminSuccessfooter = new JLabel(" ");
		AdminSuccessfooter.setBackground(new Color(87, 58, 52));
		AdminSuccessfooter.setBounds(0, 575, 312, 43);
		AdminSuccessfooter.setOpaque(true);
		AdminSuccessPanel.add(AdminSuccessfooter);

/*-------------------------------------------------------- 사용자 결제 시작 화면 ------------------------------------------------------------*/
		
		UserStartManagerPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UserStartManagerPanel.setVisible(false);
				burgerCompositionPanel.setVisible(false);
				setMenuScreenVisible(true);
			}
		});
		
		JPanel redpanel = new JPanel();
		redpanel.setBackground(Color.RED);
		redpanel.setBounds(0, 460, 312, 158);
		UserStartManagerPanel.add(redpanel);
		redpanel.setLayout(null);
		
		JLabel touchLabel = new JLabel("화면을 터치하세요!");
		touchLabel.setBounds(65, 25, 179, 33);
		redpanel.add(touchLabel);
		touchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		touchLabel.setForeground(Color.WHITE);
		touchLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 20));
		
		JLabel lblNewLabel_1 = new JLabel("Burger");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(87, 58, 52));
		lblNewLabel_1.setFont(new Font("Modak", Font.PLAIN, 40));
		lblNewLabel_1.setBounds(87, 81, 130, 50);
		UserStartManagerPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("King");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(87, 58, 52));
		lblNewLabel_2.setFont(new Font("Modak", Font.PLAIN, 40));
		lblNewLabel_2.setBounds(109, 131, 92, 50);
		UserStartManagerPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/startLogoImage.png")));
		lblNewLabel_3.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/startLogoImage.png")));
		lblNewLabel_3.setBounds(76, 193, 155, 173);
		UserStartManagerPanel.add(lblNewLabel_3);
		setSize(312, 646);
		
/*--------------------------------------------- Menu Screen -------------------------------------------------------*/
		
		try {
            // 모든 Type 정보에 대해 isVisible이 1인 Menu 정보 가져오기
            menuByTypeDTOs = MenuDAO.getMenuByTypes();

            for (int i = 0; i < menuByTypeDTOs.size(); i++) {
                MenuTypeVO menuTypeVo = menuByTypeDTOs.get(i).getMenuTypeVo();
                ArrayList<MenuVO> menuVos = menuByTypeDTOs.get(i).getMenuVos();

                // 메뉴 타입 버튼 생성
                RoundedButton typeButton = new RoundedButton(menuTypeVo.getName());
        		typeButton.setBackground(new Color(250, 242, 205));
        		typeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
        		buttonPanel.add(typeButton);
        		typeButtons.add(typeButton);
        		
        		// 타입별로 메뉴 패널 생성 및 menuPanels에 추가
        		JPanel menuPanel = new JPanel();
        		menuPanel.setBackground(new Color(255, 253, 240));
        		menuPanel.setLayout(new GridLayout(0, 3));
        		menuPanel.setVisible(true);
        		menuByTypePanels.add(menuPanel);
        		
        		// 스크롤 가능한 패널 생성
                JScrollPane scrollPane = new JScrollPane(menuPanel);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // 가로 스크롤 막기
                scrollPane.setBounds(0, 99, 312, 343);
                scrollPane.setBorder(null);	// 테두리 없애기
                frmBurgerkingKiosk.getContentPane().add(scrollPane);
                scrollPane.setVisible(false);
                scrollPanes.add(scrollPane);
                
                for (int j = 0; j < menuVos.size(); j++) {
                	int currentIndex = j;
            		JPanel menu = new JPanel();
            		menu.setOpaque(false);
            		menu.setPreferredSize(new Dimension(94, 95));
            		if (!menuByTypePanels.isEmpty())	menuByTypePanels.get(i).add(menu);
            		menu.setLayout(null);
            		menu.addMouseListener(new MouseAdapter() {
            			@Override
            			public void mouseClicked(MouseEvent e) {
            				if(menuVos.get(currentIndex).getTypeId() <= 3) {
            					menubytype_index = currentIndex;	// 어떤 타입 별 메뉴 번호(menubytype_index)가 눌렸는지 업데이트
                				setCompositionName(menuVos.get(currentIndex).getName());
                				nextComposition(scrollPane, burgerCompositionPanel);
            				} else {
            					// 버거가 아닌 메뉴(사이드, 음료&디저트)를 선택했을 때는 바로 장바구니에 담기는 로직 구현
            					menubytype_index = currentIndex;	// 어떤 타입 별 메뉴 번호(menubytype_index)가 눌렸는지 업데이트
            					basket.add(new OrderMenuDTO(1, 0, 0, menuVos.get(menubytype_index).getPrice(), menuVos.get(menubytype_index).getId()));	// 장바구니에 메뉴 담기 
            				}
            			}
            		});

            		JLabel menuName = new JLabel(menuVos.get(j).getName());
            		menuName.setBounds(0, 63, 94, 15);
            		menu.add(menuName);
            		menuName.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));
            		menuName.setHorizontalAlignment(SwingConstants.CENTER);

            		JLabel menuPrice = new JLabel(menuVos.get(j).getPrice()+"원~");
            		menuPrice.setBounds(0, 77, 94, 15);
            		menu.add(menuPrice);
            		menuPrice.setForeground(new Color(255, 0, 0));
            		menuPrice.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 13));
            		menuPrice.setHorizontalAlignment(SwingConstants.CENTER);

            		JLabel menuImage = new JLabel(" ");
            		menuImage.setBounds(0, 0, 94, 62);
            		menu.add(menuImage);
            		menuImage.setOpaque(false);
            		menuImage.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/whopper.png")));
                }
            }
            
            for(int i = 0; i < typeButtons.size(); i++) {
            	int currentIndex = i; // 현재 인덱스 저장
            	typeButtons.get(i).addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// 모든 scrollPanes의 scrollPane들을 숨김
			            for (int j = 0; j < scrollPanes.size(); j++) {
			            	scrollPanes.get(j).setVisible(false);
			            }

			            // 현재 클릭된 버튼에 해당하는 scrollPane을 보이도록 설정
			            if (currentIndex < scrollPanes.size()) {
			            	type_index = currentIndex;	// type_index에 현재 선택된 타입이 무엇인지 업데이트
			            	scrollPanes.get(type_index).setVisible(true);
			            }
					}
				});
            }
            
            scrollPanes.get(type_index).setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

/*------------------------------------------------------------- totalPanel -------------------------------------------------------------------------*/

		// 총 메뉴 주문 개수
		JLabel countLabel = new JLabel(count + "개");
		countLabel.setForeground(new Color(255, 255, 255));
		countLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 15));
		countLabel.setHorizontalAlignment(SwingConstants.CENTER);
		countLabel.setBounds(71, 14, 29, 15);
		totalPanel.add(countLabel);

		RoundedButton btnNewButton = new RoundedButton(" ");
		btnNewButton.setBackground(new Color(255, 0, 0));
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(60, 11, 50, 23);
		totalPanel.add(btnNewButton);

		// 총 주문 금액
		JLabel totalPriceLabel = new JLabel(totalPrice + "원");
		totalPriceLabel.setForeground(new Color(255, 0, 0));
		totalPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPriceLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 15));
		totalPriceLabel.setBounds(207, 14, 79, 15);
		totalPanel.add(totalPriceLabel);

		// 결제하기 버튼
		RoundedButton payButton = new RoundedButton("결제하기");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				totalPanel.setVisible(false);
				buttonPanel.setVisible(false);
				burgerCompositionPanel.setVisible(false);
				toGoPanel.setVisible(true);
			}
		});
		payButton.setForeground(new Color(255, 255, 255));
		payButton.setBackground(Color.RED);
		payButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		payButton.setBounds(175, 97, 100, 23);
		totalPanel.add(payButton);

		// 결제 취소 버튼
		RoundedButton cancelButton = new RoundedButton("취소");
		cancelButton.setForeground(new Color(255, 255, 255));
		cancelButton.setBackground(new Color(87, 58, 52));
		cancelButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		cancelButton.setBounds(34, 97, 100, 23);
		totalPanel.add(cancelButton);

		// 총 주문금액
		JLabel totalPriceLabel2 = new JLabel("총 주문금액");
		totalPriceLabel2.setFont(new Font("나눔고딕", Font.BOLD, 15));
		totalPriceLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceLabel2.setBounds(140, 14, 86, 15);
		totalPanel.add(totalPriceLabel2);

		// 카트
		JLabel cart = new JLabel("카트");
		cart.setHorizontalAlignment(SwingConstants.CENTER);
		cart.setFont(new Font("나눔고딕", Font.BOLD, 15));
		cart.setBounds(22, 13, 29, 15);
		totalPanel.add(cart);

		JLabel yellowFooter = new JLabel(" ");
		yellowFooter.setForeground(new Color(245, 233, 171));
		yellowFooter.setBackground(new Color(250, 242, 205));
		yellowFooter.setBounds(0, 0, 312, 136);
		yellowFooter.setOpaque(true);
		totalPanel.add(yellowFooter);

/*----------------------------------------------------------- footer Panel --------------------------------------------------------------------*/

		// 관리자 화면으로 가는 아이콘
		JLabel settingIcon = new JLabel(" ");
		settingIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				type_index = 0;
				AdminSuccessPanel.setVisible(false);
				loginPanel.setVisible(true);
				buttonPanel.setVisible(false);
				footerPanel.setVisible(false);
				totalPanel.setVisible(false);
				burgerCompositionPanel.setVisible(false);
				burgerMenuCompositionPanel.setVisible(false);
				orderCheck.setVisible(false);
				if(scrollPanes.size() > 0) {
					scrollPanes.get(type_index).setVisible(false);
				}
			}
		});
		settingIcon.setHorizontalAlignment(SwingConstants.CENTER);
		settingIcon.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/setting.png")));
		settingIcon.setBounds(5, 5, 38, 30);
		footerPanel.add(settingIcon);

		// 나가기 버튼: 시작 화면으로
		RoundedButton toFirstPage = new RoundedButton("나가기");
		toFirstPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type_index = 0;	
				UserStartManagerPanel.setVisible(true);
				buttonPanel.setVisible(false);
				footerPanel.setVisible(false);
				totalPanel.setVisible(false);
				burgerCompositionPanel.setVisible(false);
				burgerMenuCompositionPanel.setVisible(false);
				setIngredientAdditionScreenVisible(false);
				setSideSelectionScreenVisible(false);
				drinkPanel.setVisible(false);
				loginPanel.setVisible(false);
				if(scrollPanes.size() > 0) {
					scrollPanes.get(type_index).setVisible(false);
				}
//				toGoPanel.setVisible(false);
//				orderCheck.setVisible(false);
			}
		});
		toFirstPage.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		toFirstPage.setHorizontalAlignment(SwingConstants.RIGHT);
		toFirstPage.setForeground(new Color(255, 254, 240));
		toFirstPage.setBackground(new Color(87, 58, 52));
		toFirstPage.setBounds(226, 1, 86, 38);
		footerPanel.add(toFirstPage);

		JLabel footer = new JLabel(" ");
		footer.setBounds(0, 0, 312, 44);
		footerPanel.add(footer);
		footer.setBackground(new Color(87, 58, 52));
		footer.setOpaque(true);

/*------------------------------------------------- burgerCompositionPanel -------------------------------------------------------*/
		
		checked = new ImageIcon(BurgerKingMain.class.getResource("/images/checkButton1.png"));
		unchecked = new ImageIcon(BurgerKingMain.class.getResource("/images/checkButton.png"));
		
		lblNewLabel = new JLabel("치즈와퍼+프렌치프라이+콜라");
		lblNewLabel.setForeground(new Color(87, 58, 52));
		lblNewLabel.setFont(new Font("나눔고딕", Font.PLAIN, 13));
		lblNewLabel.setBounds(57, 222, 243, 30);
		burgerCompositionPanel.add(lblNewLabel);

		lblNewLabel2 = new JLabel("치즈와퍼 단품");
		lblNewLabel2.setForeground(new Color(87, 58, 52));
		lblNewLabel2.setFont(new Font("나눔고딕", Font.PLAIN, 13));
		lblNewLabel2.setBounds(57, 317, 243, 30);
		burgerCompositionPanel.add(lblNewLabel2);

		JLabel compositionSelectLabel = new JLabel("원하시는 구성을 선택하세요");
		compositionSelectLabel.setForeground(new Color(87, 58, 52));
		compositionSelectLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
		compositionSelectLabel.setHorizontalAlignment(SwingConstants.CENTER);
		compositionSelectLabel.setBounds(21, 95, 265, 50);
		burgerCompositionPanel.add(compositionSelectLabel);

		for (int i = 0; i < 2; i++) {
			burgerCompositionJb[i] = new JRadioButton();
			burgerCompositionJb[i].setIcon(unchecked);
			burgerCompositionJb[i].setSelectedIcon(checked);
			burgerCompositionJb[i].setForeground(new Color(87, 58, 52));
			burgerCompositionJb[i].setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
			burgerCompositionJb[i].setBackground(new Color(255, 254, 244));
			burgerCompositionPanel.add(burgerCompositionJb[i]);
			compositionBg.add(burgerCompositionJb[i]);
		}

		burgerCompositionJb[0].setText("치즈와퍼 세트");
		burgerCompositionJb[0].setBounds(35, 180, 265, 50);
		
		burgerCompositionJb[1].setText("치즈와퍼 단품");
		burgerCompositionJb[1].setBounds(35, 276, 265, 50);

		RoundedButton compositionBtn = new RoundedButton("확인");
		compositionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!burgerCompositionJb[0].isSelected() && !burgerCompositionJb[1].isSelected()) {
					JOptionPane.showMessageDialog(null, "구성을 선택해주세요.", "Message", JOptionPane.ERROR_MESSAGE);
				}
				else if(burgerCompositionJb[0].isSelected()) {	// 세트 메뉴를 선택한 경우
					updateMenuCompositionPanel(0, type_index, menubytype_index);
					burgerCompositionPanel.setVisible(false);
					footerPanel.setVisible(true);
					burgerMenuCompositionPanel.setVisible(true);
				}
				else if(burgerCompositionJb[1].isSelected()){	// 단품 메뉴를 선택한 경우
					updateMenuCompositionPanel(1, type_index, menubytype_index);
					burgerCompositionPanel.setVisible(false);
					footerPanel.setVisible(true);
					burgerMenuCompositionPanel.setVisible(true);
				}
			}
		});
		compositionBtn.setForeground(new Color(255, 253, 240));
		compositionBtn.setBackground(new Color(87, 58, 52));
		compositionBtn.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 15));
		compositionBtn.setBounds(40, 479, 220, 50);
		burgerCompositionPanel.add(compositionBtn);

		JLabel compositionToPreviousPage = new JLabel("X");
		compositionToPreviousPage.setBounds(270, 14, 19, 15);
		burgerCompositionPanel.add(compositionToPreviousPage);
		compositionToPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				burgerCompositionPanel.setVisible(false);
				buttonPanel.setVisible(true);
				scrollPanes.get(type_index).setVisible(true);
				totalPanel.setVisible(true);
				
				// Reset the state of burgerCompositionJb
				burgerCompositionJb[0].setSelected(true);
			}
		});
		compositionToPreviousPage.setForeground(new Color(87, 58, 52));
		compositionToPreviousPage.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 17));
		
/*---------------------------------------------------------------burgerMenuCompositionPanel 패널--------------------------------------------*/
		
		JLabel lblNewLabel_4 = new JLabel("선택한 메뉴를 확인해주세요");
		lblNewLabel_4.setForeground(new Color(87, 58, 52));
		lblNewLabel_4.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(12, 65, 270, 50);
		burgerMenuCompositionPanel.add(lblNewLabel_4);
		
		ingredientLabel = new JLabel("치즈와퍼");
		ingredientLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		ingredientLabel.setForeground(new Color(87, 58, 52));
		ingredientLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ingredientLabel.setBounds(81, 125, 139, 40);
		burgerMenuCompositionPanel.add(ingredientLabel);
		
		sideLabel = new JLabel("프렌치프라이");
		sideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sideLabel.setForeground(new Color(87, 58, 52));
		sideLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		sideLabel.setBounds(12, 235, 270, 40);
		burgerMenuCompositionPanel.add(sideLabel);
		
		drinkLabel = new JLabel("코카콜라");
		drinkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		drinkLabel.setForeground(new Color(87, 58, 52));
		drinkLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		drinkLabel.setBounds(31, 354, 224, 40);
		burgerMenuCompositionPanel.add(drinkLabel);
		
		changeIngredientBtn = new RoundedButton("재료 추가");
		changeIngredientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				burgerMenuCompositionPanel.setVisible(false);
				setIngredientAdditionScreenVisible(true);	// 재료 구성 선택 화면으로 변경
			}
		});
		changeIngredientBtn.setFont(new Font("나눔고딕", Font.BOLD, 15));
		changeIngredientBtn.setForeground(new Color(255, 254, 244));
		changeIngredientBtn.setBackground(new Color(87, 58, 52));
		changeIngredientBtn.setBounds(91, 175, 120, 40);
		burgerMenuCompositionPanel.add(changeIngredientBtn);
		
		changeSideBtn = new RoundedButton("사이드 변경");
		changeSideBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				burgerMenuCompositionPanel.setVisible(false);
				setSideSelectionScreenVisible(true);	// 사이드 선택 화면으로 변경
			}
		});
		changeSideBtn.setFont(new Font("나눔고딕", Font.BOLD, 15));
		changeSideBtn.setForeground(new Color(255, 254, 244));
		changeSideBtn.setBackground(new Color(87, 58, 52));
		changeSideBtn.setBounds(91, 291, 120, 40);
		burgerMenuCompositionPanel.add(changeSideBtn);
		
		changeDrinkBtn = new RoundedButton("음료 변경");
		changeDrinkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				burgerMenuCompositionPanel.setVisible(false);
				setDrinkSelectionScreenVisible(true);	// 음료 선택 화면으로 변경
			}
		});
		changeDrinkBtn.setFont(new Font("나눔고딕", Font.BOLD, 15));
		changeDrinkBtn.setForeground(new Color(255, 254, 244));
		changeDrinkBtn.setBackground(new Color(87, 58, 52));
		changeDrinkBtn.setBounds(91, 411, 120, 40);
		burgerMenuCompositionPanel.add(changeDrinkBtn);
		
		RoundedButton addToCartbtn = new RoundedButton("카트 담기");
		addToCartbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				burgerMenuCompositionPanel.setVisible(false);
				setMenuScreenVisible(true);
			}
		});
		addToCartbtn.setFont(new Font("나눔고딕", Font.BOLD, 15));
		addToCartbtn.setForeground(new Color(255, 254, 244));
		addToCartbtn.setBackground(new Color(87, 58, 52));
		addToCartbtn.setBounds(31, 498, 243, 40);
		burgerMenuCompositionPanel.add(addToCartbtn);
		
		JLabel toPreviousPage = new JLabel("X");
		toPreviousPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				burgerMenuCompositionPanel.setVisible(false);
				burgerCompositionPanel.setVisible(true);
			}
		});
		toPreviousPage.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		toPreviousPage.setForeground(new Color(87, 58, 52));
		toPreviousPage.setHorizontalAlignment(SwingConstants.RIGHT);
		toPreviousPage.setBounds(270, 14, 18, 15);
		burgerMenuCompositionPanel.add(toPreviousPage);
	
/*---------------------------------------------------------- Ingredient Add Screen --------------------------------------------------*/
		
		try {
			// 모든 버거 재료 정보 가져오기
			burgerIngredientVOs = BurgerIngredientDAO.getAllIngredients();
			ingredientjb = new JCheckBox[burgerIngredientVOs.size()];
			
			JLabel addIngredientLabel = new JLabel("재료 추가");
			addIngredientLabel.setForeground(new Color(87, 58, 52));
			addIngredientLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
			addIngredientLabel.setBounds(20, 54, 234, 40);
			ingredientPanel.add(addIngredientLabel);
			
			for (int i = 0; i < burgerIngredientVOs.size(); i++) {
				ingredientjb[i] = new JCheckBox();
				ingredientjb[i].setIcon(unchecked);
				ingredientjb[i].setSelectedIcon(checked);
				ingredientjb[i].setBackground(new Color(255, 254, 244));
				ingredientjb[i].setFont(new Font("나눔고딕", Font.BOLD, 17));
				ingredientjb[i].setForeground(new Color(87, 58, 52));
				ingredientjb[i].setText(burgerIngredientVOs.get(i).getName());
				ingredientListPanel.add(ingredientjb[i]);
			}
			
			RoundedButton btnNewButton_1 = new RoundedButton("확인");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnNewButton_1.setForeground(new Color(255, 254, 244));
			btnNewButton_1.setBackground(new Color(87, 58, 52));
			btnNewButton_1.setBounds(28, 6, 249, 50);
			ingredientFooterPanel.add(btnNewButton_1);
		} catch(Exception e) {
			System.out.println("Ingredient Add Screen Error: " + e.getMessage());
		}
		
/*---------------------------------------------------------- Side Selection Screen --------------------------------------------------*/
		
		try {
			sidejb = new JRadioButton[menuByTypeDTOs.get(3).getMenuVos().size()];
			
			JLabel changeSideLabel = new JLabel("사이드 변경");
			changeSideLabel.setForeground(new Color(87, 58, 52));
			changeSideLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
			changeSideLabel.setBounds(20, 54, 234, 40);
			sidePanel.add(changeSideLabel);
			
			for(int i = 0; i < menuByTypeDTOs.get(3).getMenuVos().size(); i++) {
				sidejb[i] = new JRadioButton();
				sidejb[i].setIcon(unchecked);
				sidejb[i].setSelectedIcon(checked);
				sidejb[i].setBackground(new Color(255, 254, 244));
				sidejb[i].setFont(new Font("나눔고딕", Font.BOLD, 17));
				sidejb[i].setForeground(new Color(87, 58, 52));
				sidejb[i].setText(menuByTypeDTOs.get(3).getMenuVos().get(i).getName());
				sidebg.add(sidejb[i]);
				sideListPanel.add(sidejb[i]);
			}
			
			RoundedButton btnNewButton_2 = new RoundedButton("확인");
			btnNewButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			btnNewButton_2.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnNewButton_2.setForeground(new Color(255, 254, 244));
			btnNewButton_2.setBackground(new Color(87, 58, 52));
			btnNewButton_2.setBounds(28, 6, 249, 50);
			sideFooterPanel.add(btnNewButton_2);
		} catch(Exception e) {
			System.out.println("Side Selection Screen Error: " + e.getMessage());
		}
		
/*-------------------------------------------------------- Drink Selection Screen --------------------------------------------------*/
		
		try {
			drinkjb = new JRadioButton[menuByTypeDTOs.get(4).getMenuVos().size()];
			
			JLabel changeDrinkLabel = new JLabel("음료 변경");
			changeDrinkLabel.setForeground(new Color(87, 58, 52));
			changeDrinkLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
			changeDrinkLabel.setBounds(20, 54, 234, 40);
			drinkPanel.add(changeDrinkLabel);
			
			for(int i = 0; i < menuByTypeDTOs.get(4).getMenuVos().size(); i++) {
				drinkjb[i] = new JRadioButton();
				drinkjb[i].setIcon(unchecked);
				drinkjb[i].setSelectedIcon(checked);
				drinkjb[i].setBackground(new Color(255, 254, 244));
				drinkjb[i].setFont(new Font("나눔고딕", Font.BOLD, 17));
				drinkjb[i].setForeground(new Color(87, 58, 52));
				drinkjb[i].setText(menuByTypeDTOs.get(4).getMenuVos().get(i).getName());
				drinkbg.add(drinkjb[i]);
				drinkListPanel.add(drinkjb[i]);
			}
			
			RoundedButton btnNewButton_3 = new RoundedButton("확인");
			btnNewButton_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnNewButton_3.setFont(new Font("맑은 고딕", Font.BOLD, 16));
			btnNewButton_3.setForeground(new Color(255, 254, 244));
			btnNewButton_3.setBackground(new Color(87, 58, 52));
			btnNewButton_3.setBounds(28, 6, 249, 50);
			drinkFooterPanel.add(btnNewButton_3);
		} catch(Exception e) {
			System.out.println("Drink Selection Screen Error: " + e.getMessage());
		}
	
/*----------------------------------------------------------toGoPanel ------------------------------------------------
		
		for (int i = 0; i < 2; i++) {
			whereToEatjb[i] = new JRadioButton();
			whereToEatjb[i].setIcon(unchecked);
			whereToEatjb[i].setSelectedIcon(checked);
			whereToEatjb[i].setBackground(new Color(255, 254, 244));
			whereToEatjb[i].setFont(new Font("나눔고딕", Font.BOLD, 17));
			whereToEatjb[i].setForeground(new Color(87, 58, 52));
			bg3.add(whereToEatjb[i]);
		}
		
		whereToEatjb[0].setText(whereToEat[0]);
		whereToEatjb[1].setText(whereToEat[1]);
		whereToEatjb[0].setBounds(22, 159, 220, 40);
		whereToEatjb[1].setBounds(22, 255, 220, 40);
	
		
		JLabel chooseToGoLabel = new JLabel("선택해주세요");
		chooseToGoLabel.setForeground(new Color(87, 58, 52));
		chooseToGoLabel.setFont(new Font("나눔고딕", Font.BOLD, 18));
		chooseToGoLabel.setBounds(22, 84, 234, 40);
		toGoPanel.add(chooseToGoLabel);
		toGoPanel.add(whereToEatjb[0]);
		toGoPanel.add(whereToEatjb[1]);
		
		JLabel lblNewLabel_6 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_6.setBounds(20, 125, 258, 15);
		lblNewLabel_6.setForeground(new Color(87, 58, 52));
		toGoPanel.add(lblNewLabel_6);
		
		RoundedButton btnNewButton_111 = new RoundedButton("확인");
		btnNewButton_111.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		btnNewButton_111.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toGoPanel.setVisible(false);
				orderCheck.setVisible(true);
				labelPanel.setVisible(true);
				labelFooter.setVisible(true);
			}
		});
		btnNewButton_111.setForeground(new Color(255, 254, 244));
		btnNewButton_111.setBackground(new Color(87, 58, 52));
		btnNewButton_111.setBounds(22, 499, 249, 50);
		toGoPanel.add(btnNewButton_111);
		
		JLabel toPreviousPage1111 = new JLabel("X");
		toPreviousPage1111.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				toGoPanel.setVisible(false);
				buttonPanel.setVisible(true);
				whopperPanel.setVisible(true);
				totalPanel.setVisible(true);
				footerPanel.setVisible(true);
			}
		});
		toPreviousPage1111.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		toPreviousPage1111.setForeground(new Color(87, 58, 52));
		toPreviousPage1111.setHorizontalAlignment(SwingConstants.RIGHT);
		toPreviousPage1111.setBounds(222, 10, 60, 15);
		toGoPanel.add(toPreviousPage1111);----------------------------------------*/
/*--------------------------------------------------------------orderCheckPanel--------------------------------------------------
		
		//주문확인 패널
			
		orderPanel = new JPanel();
		orderPanel.setBounds(10, 10, 280, 180);
		orderPanel.setBackground(new Color(255, 254, 244));
		orderCheck.add(orderPanel);
		orderPanel.setLayout(null);
		
		JLabel orderMenuLabel = new JLabel("치즈와퍼세트");
		orderMenuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		orderMenuLabel.setForeground(new Color(87, 58, 52));
		orderMenuLabel.setBounds(12, 6, 95, 20);
		orderPanel.add(orderMenuLabel);
		
		JLabel menuPrice = new JLabel("9000" + "원");
		menuPrice.setForeground(new Color(255, 0, 0));
		menuPrice.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		menuPrice.setBounds(12, 32, 67, 15);
		orderPanel.add(menuPrice);
		
		JLabel lblNewLabel_61_1 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_61_1.setForeground(new Color(87, 58, 52));
		lblNewLabel_61_1.setBounds(0, 48, 258, 15);
		orderPanel.add(lblNewLabel_61_1);
		
		JLabel sideOrderLabel = new JLabel("사이드");
		sideOrderLabel.setForeground(new Color(87, 58, 52));
		sideOrderLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		sideOrderLabel.setBounds(12, 60, 50, 15);
		orderPanel.add(sideOrderLabel);
		
		JLabel sideOrderLabel_1 = new JLabel("수량");
		sideOrderLabel_1.setForeground(new Color(87, 58, 52));
		sideOrderLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		sideOrderLabel_1.setBounds(12, 91, 50, 15);
		orderPanel.add(sideOrderLabel_1);
		
		JLabel lblNewLabel_61_1_1 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_61_1_1.setForeground(new Color(87, 58, 52));
		lblNewLabel_61_1_1.setBounds(0, 73, 258, 15);
		orderPanel.add(lblNewLabel_61_1_1);
		
		JLabel sideOrder = new JLabel("코울슬로" + " 교환");
		sideOrder.setHorizontalAlignment(SwingConstants.RIGHT);
		sideOrder.setForeground(new Color(87, 58, 52));
		sideOrder.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		sideOrder.setBounds(149, 60, 95, 15);
		orderPanel.add(sideOrder);
		
		JLabel lblNewLabel_61_1_1_1 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_61_1_1_1.setForeground(new Color(87, 58, 52));
		lblNewLabel_61_1_1_1.setBounds(0, 108, 258, 15);
		orderPanel.add(lblNewLabel_61_1_1_1);
		
		JLabel totalOrderPriceLabel = new JLabel("합계금액");
		totalOrderPriceLabel.setForeground(new Color(87, 58, 52));
		totalOrderPriceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		totalOrderPriceLabel.setBounds(12, 125, 95, 15);
		orderPanel.add(totalOrderPriceLabel);
		
		JLabel totalOrderPrice = new JLabel("18000" + " 원");
		totalOrderPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		totalOrderPrice.setForeground(new Color(255, 0, 0));
		totalOrderPrice.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		totalOrderPrice.setBounds(149, 125, 95, 15);
		orderPanel.add(totalOrderPrice);
		
		JLabel menuCount = new JLabel("2");
		menuCount.setForeground(new Color(87, 58, 52));
		menuCount.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		menuCount.setHorizontalAlignment(SwingConstants.CENTER);
		menuCount.setBounds(186, 91, 29, 15);
		orderPanel.add(menuCount);
		
		JLabel minus = new JLabel("");
		minus.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/minus.png")));
		minus.setBounds(159, 87, 29, 20);
		orderPanel.add(minus);
		
		JLabel plus = new JLabel("");
		plus.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/plus.png")));
		plus.setBounds(215, 87, 29, 20);
		orderPanel.add(plus);
		
		
		//메뉴 선택 두번째
		orderPanel1 = new JPanel();
		orderPanel1.setBounds(10, 210, 280, 180);
		orderPanel1.setBackground(new Color(255, 254, 244));
		orderCheck.add(orderPanel1);
		orderPanel1.setLayout(null);
		
		JLabel orderMenuLabel1 = new JLabel("치즈와퍼세트");
		orderMenuLabel1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		orderMenuLabel1.setForeground(new Color(87, 58, 52));
		orderMenuLabel1.setBounds(12, 6, 95, 20);
		orderPanel1.add(orderMenuLabel1);
		
		JLabel menuPrice1 = new JLabel("9000" + "원");
		menuPrice1.setForeground(new Color(255, 0, 0));
		menuPrice1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		menuPrice1.setBounds(12, 32, 67, 15);
		orderPanel1.add(menuPrice1);
		
		JLabel lblNewLabel_61_11 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_61_11.setForeground(new Color(87, 58, 52));
		lblNewLabel_61_11.setBounds(0, 48, 258, 15);
		orderPanel1.add(lblNewLabel_61_11);
		
		JLabel sideOrderLabel1 = new JLabel("사이드");
		sideOrderLabel1.setForeground(new Color(87, 58, 52));
		sideOrderLabel1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		sideOrderLabel1.setBounds(12, 60, 50, 15);
		orderPanel1.add(sideOrderLabel1);
		
		JLabel sideOrderLabel_11 = new JLabel("수량");
		sideOrderLabel_11.setForeground(new Color(87, 58, 52));
		sideOrderLabel_11.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		sideOrderLabel_11.setBounds(12, 91, 50, 15);
		orderPanel1.add(sideOrderLabel_11);
		
		JLabel lblNewLabel_61_1_11 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_61_1_11.setForeground(new Color(87, 58, 52));
		lblNewLabel_61_1_11.setBounds(0, 73, 258, 15);
		orderPanel1.add(lblNewLabel_61_1_11);
		
		JLabel sideOrder1 = new JLabel("코울슬로" + " 교환");
		sideOrder1.setHorizontalAlignment(SwingConstants.RIGHT);
		sideOrder1.setForeground(new Color(87, 58, 52));
		sideOrder1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		sideOrder1.setBounds(149, 60, 95, 15);
		orderPanel1.add(sideOrder1);
		
		JLabel lblNewLabel_61_1_1_11 = new JLabel("----------------------------------------------------------------");
		lblNewLabel_61_1_1_11.setForeground(new Color(87, 58, 52));
		lblNewLabel_61_1_1_11.setBounds(0, 108, 258, 15);
		orderPanel1.add(lblNewLabel_61_1_1_11);
		
		JLabel totalOrderPriceLabel1 = new JLabel("합계금액");
		totalOrderPriceLabel1.setForeground(new Color(87, 58, 52));
		totalOrderPriceLabel1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		totalOrderPriceLabel1.setBounds(12, 125, 95, 15);
		orderPanel1.add(totalOrderPriceLabel1);
		
		JLabel totalOrderPrice1 = new JLabel("18000" + " 원");
		totalOrderPrice1.setHorizontalAlignment(SwingConstants.RIGHT);
		totalOrderPrice1.setForeground(new Color(255, 0, 0));
		totalOrderPrice1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		totalOrderPrice1.setBounds(149, 125, 95, 15);
		orderPanel1.add(totalOrderPrice1);
		
		JLabel menuCount1 = new JLabel("2");
		menuCount1.setForeground(new Color(87, 58, 52));
		menuCount1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		menuCount1.setHorizontalAlignment(SwingConstants.CENTER);
		menuCount1.setBounds(186, 91, 29, 15);
		orderPanel1.add(menuCount1);
		
		JLabel minus1 = new JLabel("");
		minus1.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/minus.png")));
		minus1.setBounds(159, 87, 29, 20);
		orderPanel1.add(minus1);
		
		JLabel plus1 = new JLabel("");
		plus1.setIcon(new ImageIcon(BurgerKingMain.class.getResource("/images/plus.png")));
		plus1.setBounds(215, 87, 29, 20);
		orderPanel1.add(plus1);
		
		orderPanel2 = new JPanel();
		orderPanel2.setBounds(10, 490, 280, 180);
		orderPanel2.setBackground(new Color(255, 254, 244));
		orderCheck.add(orderPanel2);
		orderPanel2.setLayout(null);---------------------------------*/
		
/*--------------------------------------------------------주문확인 header-----------------------------------------------------------
		JLabel orderCheckLabel = new JLabel("주문확인");
		orderCheckLabel.setForeground(new Color(87, 58, 52));
		orderCheckLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		orderCheckLabel.setHorizontalAlignment(SwingConstants.CENTER);
		orderCheckLabel.setBounds(0, 63, 300, 26);
		labelPanel.add(orderCheckLabel);-----------------------------------------*/

/*----------------------------------------------------------주문확인 footer----------------------------------------------------------

				// 총 주문 금액
		JLabel totalPriceLabel1 = new JLabel(totalPrice + "원");
		totalPriceLabel1.setForeground(new Color(255, 0, 0));
		totalPriceLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		totalPriceLabel1.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 15));
		totalPriceLabel1.setBounds(207, 14, 79, 15);
		labelFooter.add(totalPriceLabel1);

				// 결제하기 버튼
		RoundedButton payButton1 = new RoundedButton("결제하기");
		payButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toGoPanel.setVisible(true);
				totalPanel.setVisible(false);
				buttonPanel.setVisible(false);
				whopperPanel.setVisible(false);
				burgerCompositionPanel.setVisible(false);
			}
		});
		payButton1.setForeground(new Color(255, 255, 255));
		payButton1.setBackground(Color.RED);
		payButton1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		payButton1.setBounds(175, 97, 100, 23);
		labelFooter.add(payButton1);

				// 결제 취소 버튼
		RoundedButton cancelButton1 = new RoundedButton("취소");
		cancelButton1.setForeground(new Color(255, 255, 255));
		cancelButton1.setBackground(new Color(87, 58, 52));
		cancelButton1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
		cancelButton1.setBounds(34, 97, 100, 23);
		labelFooter.add(cancelButton1);

				// 총 주문금액
		JLabel totalPriceLabel21 = new JLabel("총 주문금액");
		totalPriceLabel21.setFont(new Font("나눔고딕", Font.BOLD, 15));
		totalPriceLabel21.setHorizontalAlignment(SwingConstants.CENTER);
		totalPriceLabel21.setBounds(140, 14, 86, 15);
		labelFooter.add(totalPriceLabel21);

				

		JLabel yellowFooter1 = new JLabel(" ");
		yellowFooter1.setForeground(new Color(245, 233, 171));
		yellowFooter1.setBackground(new Color(255, 254, 244));
		yellowFooter1.setBounds(0, 0, 312, 136);
		yellowFooter1.setOpaque(true);
		labelFooter.add(yellowFooter1);-----------------------------------------------*/
	}
	
/*---------------------------------------------------------- 메소드 -------------------------------------------------------------*/

	// 메뉴 선택 시 옵션을 선택하는 패널을 바꾸어주는 메소드
	private void nextComposition(JPanel nowPanel, JPanel next) {
		nowPanel.setVisible(false);
		buttonPanel.setVisible(false);
		totalPanel.setVisible(false);
		next.setVisible(true);
	}
	
	// 스크롤팬을 보이지 않게 하여 메뉴 선택 시 옵션을 선택하는 패널을 바꾸어주는 메서드
	private void nextComposition(JScrollPane scrollPane, JPanel next) {
		scrollPane.setVisible(false);
		buttonPanel.setVisible(false);
		totalPanel.setVisible(false);
		next.setVisible(true);
	}

	// composition 메뉴 이름을 바꾸어주는 메소드
	private void setCompositionName(String menuName) {
		lblNewLabel.setText(menuName+"+프렌치프라이+콜라");
		lblNewLabel2.setText(menuName + " 단품");
		burgerCompositionJb[0].setText(menuName + " 세트");
		burgerCompositionJb[1].setText(menuName + " 단품");
	}
	
	// 메뉴 화면의 setVisible을 지정해주는 메서드
	private void setMenuScreenVisible(Boolean setVisible) {
		buttonPanel.setVisible(setVisible);
		totalPanel.setVisible(setVisible);
		footerPanel.setVisible(setVisible);
		if(scrollPanes.size() > 0) {	// 메뉴 타입에 따른 메뉴판을 보여주는 스크롤 팬 리스트의 사이즈가 0보다 클 경우에만
			scrollPanes.get(type_index).setVisible(true);
		}
	}
	
	/* 버거 메뉴의 재료 추가, 사이드, 음료를 지정해 주는 버튼이 있는 패널의 메뉴 이름을 바꾸어주는 메소드(단품일 경우엔 사이드 / 음료 컴포넌트를 없애도록)
	   burgerCompositionPanel에서 사용 */
	private void updateMenuCompositionPanel(int isSingle, int currentTypeIndex, int currentMenuByTypeIndex) {
		String menuName = menuByTypeDTOs.get(currentTypeIndex).getMenuVos().get(currentMenuByTypeIndex).getName();
		ingredientLabel.setText(menuName);
		
		if(isSingle == 1) {	// 단품 버거를 선택한 경우
			sideLabel.setVisible(false);
			changeSideBtn.setVisible(false);
			drinkLabel.setVisible(false);
			changeDrinkBtn.setVisible(false);
		} else {	// 세트 버거 메뉴를 선택한 경우
			sideLabel.setVisible(true);
			changeSideBtn.setVisible(true);
			drinkLabel.setVisible(true);
			changeDrinkBtn.setVisible(true);
		}
	}
	
	// 버거 재료 추가 화면의 setVisible을 설정해주는 메서드
	public void setIngredientAdditionScreenVisible(Boolean setVisible) {
		ingredientPanel.setVisible(setVisible);
		ingredientScroll.setVisible(setVisible);
		ingredientFooterPanel.setVisible(setVisible);
	}
	
	// 버거 세트 사이드 변경 화면의 setVisible을 설정해주는 메서드
	public void setSideSelectionScreenVisible(Boolean setVisible) {
		sidePanel.setVisible(setVisible);
		sideScroll.setVisible(setVisible);
		sideFooterPanel.setVisible(setVisible);
	}
	
	// 버거 세트 음료 변경 화면의 setVisible을 설정해주는 메서드
	public void setDrinkSelectionScreenVisible(Boolean setVisible) {
		drinkPanel.setVisible(setVisible);
		drinkScroll.setVisible(setVisible);
		drinkFooterPanel.setVisible(setVisible);
	}
	
	private void clearTypeAndMenuIndex() {
		type_index = 0;
		menubytype_index = 0;
	}

/*---------------------------------------------------- Main --------------------------------------------------------*/

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BurgerKingMain window = new BurgerKingMain();
					window.frmBurgerkingKiosk.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
