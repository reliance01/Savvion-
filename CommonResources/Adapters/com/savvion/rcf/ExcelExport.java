
package com.savvion.rcf;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.savvion.rcf.bean.BranchWaitingBean;
import com.savvion.rcf.bean.CourierMISBean;
import com.savvion.rcf.bean.DailyCOPSBean;
import com.savvion.rcf.bean.PacketInTransitBean;
import com.savvion.rcf.bean.VendorsFollowUpBean;
import com.savvion.sbm.bizsolo.util.SBMConf;

public class ExcelExport
{
    private static final String COLUMNS = "columns";

    private static final String DATA    = "data";

    private final String        NAME    = "name";

    public String exportToExcel( Map reports )
    {
        if ( reports == null )
        {
            throw new RuntimeException( "Cannot Export <null> data to excel." );
        }
        List< String > reportColumns = ( List< String > ) reports.get( COLUMNS );
        List reportData = ( List ) reports.get( DATA );
        String reportName = ( String ) reports.get( NAME )
                + new SimpleDateFormat( "dd-MM-yyyy HH-mm-ss" )
                        .format( new Date() ) + ".xls";
        OutputStream out = null;
        HSSFWorkbook wb = null;
        try
        {
            out = new BufferedOutputStream( new FileOutputStream(
                    SBMConf.SBM_WEBAPPDIR + "/bpmportal/reports/rcf_reports/"
                            + reportName ) );
            // create a new workbook
            wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet( reportName );
            sheet.setDefaultColumnWidth( ( short ) 16 );

            // ***************** Font Format of Header************************//
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints( ( short ) 10 );
            font.setFontName( "Arial" );
            // font.setItalic(true);
            font.setColor( HSSFColor.BLACK.index );
            font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
            HSSFCellStyle colStyle = wb.createCellStyle();
            colStyle.setFillBackgroundColor( HSSFColor.ORANGE.index );
            colStyle.setFont( font );

            // *************creating header for excel file******************//
            HSSFRow col = sheet.createRow( ( short ) 0 );
            int colIndex = 0;
            for ( String column : reportColumns )
            {
                createDataGridColumn( col, colStyle, colIndex++, column );
            }

            // *****Font Format of DataGrid*******************//
            HSSFFont font1 = wb.createFont();
            font1.setFontHeightInPoints( ( short ) 10 );
            font1.setFontName( "Arial" );
            font1.setColor( HSSFColor.BLACK.index );
            HSSFCellStyle rowStyle = wb.createCellStyle();
            rowStyle.setFont( font1 );

            // ***********Creating Data for Excel Sheet ***********//
            HSSFRow row = null;
            short rowIndex = 0;
            for ( Object data : reportData )
            {
                row = sheet.createRow( ( short ) ++rowIndex );
                int cIndex = 0;
                if ( data instanceof CourierMISBean )
                {
                    CourierMISBean cbean = ( CourierMISBean ) data;
                    createDataGridColumn( row, rowStyle, cIndex++, cbean
                            .getPacketNo() );
                    createDataGridColumn( row, rowStyle, cIndex++, cbean
                            .getReceivedDate().toString() );
                    createDataGridColumn( row, rowStyle, cIndex++, cbean
                            .getAwbNo() );
                    createDataGridColumn( row, rowStyle, cIndex++, cbean
                            .getLocation() );
                    createDataGridColumn( row, rowStyle, cIndex++, cbean
                            .getPacketStatus() );
                }
                else if ( data instanceof DailyCOPSBean )
                {
                    DailyCOPSBean dBean = ( DailyCOPSBean ) data;
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getLocation() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getLoanNumber() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getUpdateRejectDate().toString() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getRejectReason() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getType() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getTrackerNumber() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getInwardLops().toString() );
                    createDataGridColumn( row, rowStyle, cIndex++, dBean
                            .getInwardSyntel().toString() );
                }
                else if ( data instanceof BranchWaitingBean )
                {
                    BranchWaitingBean bBean = ( BranchWaitingBean ) data;
                    createDataGridColumn( row, rowStyle, cIndex++, String
                            .valueOf( bBean.getInsuranceCount() ) );
                    createDataGridColumn( row, rowStyle, cIndex++, String
                            .valueOf( bBean.getInvoiceCount() ) );
                    createDataGridColumn( row, rowStyle, cIndex++, String
                            .valueOf( bBean.getRcCount() ) );
                }
                else if ( data instanceof PacketInTransitBean )
                {
                    PacketInTransitBean pBean = ( PacketInTransitBean ) data;
                    createDataGridColumn( row, rowStyle, cIndex++, String
                            .valueOf( pBean.getInsuranceCount() ) );
                    createDataGridColumn( row, rowStyle, cIndex++, String
                            .valueOf( pBean.getInvoiceCount() ) );
                    createDataGridColumn( row, rowStyle, cIndex++, String
                            .valueOf( pBean.getRcCount() ) );
                }
                else if ( data instanceof VendorsFollowUpBean )
                {
                    VendorsFollowUpBean vBean = ( VendorsFollowUpBean ) data;
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getLoanNumber() );
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getDocType() );
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getSupplierName() );
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getCustomerName() );
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getAddress() );
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getPhone() );
                    createDataGridColumn( row, rowStyle, cIndex++, vBean
                            .getAssetType() );
                }
            }
        }
        catch ( Exception Ex )
        {
            System.out.println( Ex );
        }
        finally
        {
            try
            {
                if ( wb != null )
                {
                    wb.write( out );
                }
            }
            catch ( Exception ex )
            {
            }
            try
            {
                if ( out != null )
                {
                    out.close();
                }
            }
            catch ( Exception ex )
            {
            }
        }

        return reportName;
    }

    public void createDataGridColumn( HSSFRow row, HSSFCellStyle style,
            int columnIndex, String data )
    {
        HSSFCell cell = row.createCell( ( short ) columnIndex );
        cell.setCellValue( data );
        cell.setCellStyle( style );
    }
}
