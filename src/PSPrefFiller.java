import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PSPrefFiller extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String list[];
	int size;
	JPanel mainPanel, mainList, buttonContainer;
	JButton selectBtn, deselectBtn, genBtn;
	JLabel listLabels[];
	JList<String> lst;
	JList<String> prefList;
	int selectedIndex[];
	int selectedSize;
	String nullList[];
	
	PSPrefFiller()
	{
		try {
			input();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(50, 50, 1200, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		//list = new String[1000];
		//size = 0;
		/*try
		{
			input();			
		}
		catch(IOException ii)
		{
			System.out.print("invalid file formatting");
		}*/
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		add(mainPanel);
		mainPanel.setVisible(true);
		//setAllLayouts();
		//print();
	}
	private void setAllLayouts() 
	{
		lst = new JList<String>(list);
		lst.setVisibleRowCount(size);
		//lst.setBounds(0, 0, 500, 550);
		JScrollPane sp = new JScrollPane(lst, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//sp.setBounds(0, 0, 500, 550);
		//lst.setSize(500, 550);
		//sp.setSize(500, 550);
		
		nullList = new String[size];
		for(int i=0;i<size;i++)
		{
			nullList[i] = "";
		}
		prefList = new JList<String>(nullList);
		prefList.setVisibleRowCount(size);
		//lst.setBounds(0, 0, 500, 550);
		//prefList.setBounds(0, 0, 500, 550);
		JScrollPane sp1 = new JScrollPane(prefList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//sp.setBounds(0, 0, 500, 550);
		//prefList.setSize(500, 550);
		//sp1.setSize(500, 550);
		
		buttonContainer = new JPanel();
		buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
		
		selectBtn = new JButton("Select");
		deselectBtn = new JButton("Deselect");
		
		buttonContainer.add(selectBtn);
		buttonContainer.add(deselectBtn);
		
		selectBtn.addActionListener(this);
		deselectBtn.addActionListener(this);
		
		genBtn = new JButton("Generate");
		buttonContainer.add(genBtn);
		genBtn.addActionListener(this);
		
		selectedSize = 0;
		selectedIndex = new int[size];
		
		//mainList.setVisible(true);
		//mainPanel.add(mainList);
		mainPanel.add(sp);
		mainPanel.add(buttonContainer);
		mainPanel.add(sp1);
		this.revalidate();
		this.repaint();
	}
	
	private void input()throws IOException
	{
		BufferedReader dd = new BufferedReader(new FileReader("ps1 list.txt"));
		BufferedReader dd1 = new BufferedReader(new FileReader("ps1 list.txt"));
		String s;
		int mod = 0;
		int i = 0;
		try
		{
			while((s=dd.readLine())!=null)
			{
				if(mod == 1)
				{
					size++;
					//list[size] = i + ". " + s;
					//i++;
				}
				mod = (mod+1)%4;
			}
		}
		catch(EOFException ee)
		{
			
		}
		mod = 0;
		list = new String[size];
		try
		{
			while((s=dd1.readLine())!=null)
			{
				if(mod == 1)
				{
					list[i] = (i+1) + ". " + s.substring(s.indexOf(',')+1) + " -- " + s.substring(0, s.indexOf(','));
					i++;
				}
				mod = (mod+1)%4;
			}
		}
		catch(EOFException ee)
		{
			
		}
		System.out.println("Size = " + size);
	}
	
	public static void main(String[] args) throws Exception
	{
		PSPrefFiller obj = new PSPrefFiller();
		obj.setAllLayouts();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		int index;
		if(arg0.getSource() == selectBtn)
		{
			System.out.println("Selected index = " + lst.getSelectedIndex());
			index = lst.getSelectedIndex();
			System.out.println("hello " + size);
			if(index == -1 || index >= size)
				return;
			System.out.println("hello");
			
			nullList[selectedSize] = list[index];
			int j;
			for(j=index;j<size-1;j++)
			{
				list[j] = list[j+1];
			}
			size--;
			list[size] = "";
			selectedSize++;
			prefList.setListData(nullList);
			lst.setListData(list);
		}
		else if(arg0.getSource() == deselectBtn)
		{
			System.out.println(prefList.getSelectedIndex());
			index = prefList.getSelectedIndex();
			if(index == -1 || index >= selectedSize)
				return;
			
			int dotIndex = nullList[index].indexOf('.');
			int indexToInsert = Integer.parseInt(nullList[index].substring(0, dotIndex));
			int j;
			for(j=size;j>=indexToInsert;j--)
			{
				list[j] = list[j-1];
			}
			list[indexToInsert] = nullList[index];
			size++;
			
			for(j=index;j<selectedSize-1;j++)
			{
				nullList[j] = nullList[j+1];
			}
			selectedSize--;
			nullList[selectedSize] = "";
			prefList.setListData(nullList);
			lst.setListData(list);

		}
		else if(arg0.getSource() == genBtn)
		{
			PrintWriter pw;
			try
			{
				pw = new PrintWriter("PSPreferance.txt");
				for(int k=0;k<selectedSize;k++)
				{
					pw.println((k+1) + " " + nullList[k].substring(0, nullList[k].indexOf('.')) + "\t" + nullList[k].substring(nullList[k].indexOf('.')+1));
				}
				pw.close();
			}
			catch(FileNotFoundException fnf)
			{
				
			}
		
		}
		mainPanel.validate();
		mainPanel.repaint();
	}

}
