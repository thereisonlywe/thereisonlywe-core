package info.thereisonlywe.core.misc;

import javax.swing.JComponent;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRadioButtonMenuItemUI;

public class StayOpenRadioButtonMenuItemUI extends BasicRadioButtonMenuItemUI
{
	@Override
	protected void doClick(MenuSelectionManager msm)
	{
		menuItem.doClick(0);
	}

	public static ComponentUI createUI(JComponent c)
	{
		return new StayOpenRadioButtonMenuItemUI();
	}
}
