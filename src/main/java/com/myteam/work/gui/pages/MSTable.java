package com.myteam.work.gui.pages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.myteam.work.management.data.Pair;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MSTable {
	private JScrollPane sp;
	private JTable stickyTable;
	private JTable contentTable;
	private HashMap<Pair<Integer, Integer>, Object> dest;

	public MSTable(String[] columnName, List<Class<?>> contentTypes, List<Integer> contentEditableColumn) {
		var stickyColumnName = new String[] {columnName[0]};
		var contentColumnName = new String[columnName.length - 1];

		for(var i = 0; i < contentColumnName.length; i++) contentColumnName[i] = columnName[i + 1];

		var stickyTableModel = new DefaultTableModel(new Object[][]{}, stickyColumnName) {
			@Override
			public Class<?> getColumnClass(int column) {
				return Integer.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		var contentTableModel = new DefaultTableModel(new Object[][]{}, contentColumnName) {
			@Override
			public Class<?> getColumnClass(int column) {
				return contentTypes.get(column);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return contentEditableColumn.contains(column);
			}
		};
		var mlr = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				var c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

				if(isSelected) c.setBackground(UIManager.getColor("Table.selectionBackground"));

				if(value != null && value.getClass().isArray()) {
					JList<Object> list = new JList<>();
					list.setBackground(getBackground());
					list.setForeground(getForeground());
					list.setFont(c.getFont());
					list.setCellRenderer(new DefaultListCellRenderer() {
						@Override
						public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
							var label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
							label.setHorizontalAlignment(SwingConstants.CENTER);

							return label;
						}
					});

					list.setListData((Object[]) value);

					return list;
				}

				return c;
			}
		};
		mlr.setHorizontalAlignment(SwingConstants.CENTER);
		this.stickyTable = new JTable(stickyTableModel);
		this.stickyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.stickyTable.getColumnModel().getColumn(0).setCellRenderer(mlr);
		this.contentTable = new JTable(contentTableModel);
		this.contentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.contentTable.setSelectionModel(this.stickyTable.getSelectionModel());

		for(var i = 0; i < this.contentTable.getColumnCount(); i++) this.contentTable.getColumnModel().getColumn(i).setCellRenderer(mlr);

		this.contentTable.getModel().addTableModelListener(e -> {
			if(e.getType() == TableModelEvent.UPDATE) {
				if(dest == null) {
					log.error("Missing fired destination");
	
					return;
				}

				var column = e.getColumn();
				var row = e.getFirstRow();
				var key = new Pair<Integer, Integer>((Integer) stickyTable.getValueAt(row, 0), column);
				
				if(dest.containsKey(key)) dest.remove(key);

				dest.put(key, contentTable.getValueAt(row, column));
			}
		});

		this.sp = new JScrollPane(contentTable);
		var vp = new JViewport() {
			@Override
			public Dimension getPreferredSize() {
				var d = super.getPreferredSize();
				d.width = stickyTable.getPreferredSize().width;

				return d;
			}
		};
		vp.setView(this.stickyTable);
		this.sp.setRowHeader(vp);
		this.sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, this.stickyTable.getTableHeader());
	}

	public JScrollPane getDisplayer() {
		return this.sp;
	}

	public void setRowHeight(int rowHeight) {
		this.stickyTable.setRowHeight(rowHeight);
		this.contentTable.setRowHeight(rowHeight);
	}

	public void setShowGrid(boolean grid) {
		this.stickyTable.setShowGrid(grid);
		this.contentTable.setShowGrid(grid);
	}

	public void setPreferredWidth(int column, int width) {
		if(column == 0) this.stickyTable.getColumnModel().getColumn(0).setPreferredWidth(width);
		else this.contentTable.getColumnModel().getColumn(column - 1).setPreferredWidth(width);
	}

	public void setIntercellSpacing(Dimension d) {
		this.stickyTable.setIntercellSpacing(d);
		this.contentTable.setIntercellSpacing(d);
	}

	public void setSelectionMode(int lsm) {
		this.stickyTable.setSelectionMode(lsm);
		this.contentTable.setSelectionMode(lsm);
	}

	public void setReorderingColumn(boolean rc) {
		this.stickyTable.getTableHeader().setReorderingAllowed(rc);
		this.contentTable.getTableHeader().setReorderingAllowed(rc);
	}

	public void setResizingColumn(boolean rc) {
		this.stickyTable.getTableHeader().setResizingAllowed(rc);
		this.contentTable.getTableHeader().setResizingAllowed(rc);
	}

	public void clearData() {
		((DefaultTableModel) this.stickyTable.getModel()).setRowCount(0);
		((DefaultTableModel) this.contentTable.getModel()).setRowCount(0);
	}

	public void addData(Object[][] data) {
		for(Object[] datum : data) {
			((DefaultTableModel) this.stickyTable.getModel()).addRow(new Object[]{datum[0]});
			((DefaultTableModel) this.contentTable.getModel()).addRow(Arrays.copyOfRange(datum, 1, datum.length));
		}
	}

	public void setDestination(HashMap<Pair<Integer, Integer>, Object> destination) {
		this.dest = destination;
	}

	public TableModel getIDModel() {
		return this.stickyTable.getModel();
	}

	public TableModel getContentModel() {
		return this.contentTable.getModel();
	}

	public void removeRow(int i) {
		((DefaultTableModel) this.stickyTable.getModel()).removeRow(i);
		((DefaultTableModel) this.contentTable.getModel()).removeRow(i);
	}

    public int getSelectedRow() {
        return this.stickyTable.getSelectedRow();
    }

	public void addRowSelectionListener(ListSelectionListener listener) {
    this.stickyTable.getSelectionModel().addListSelectionListener(listener);
}
}
