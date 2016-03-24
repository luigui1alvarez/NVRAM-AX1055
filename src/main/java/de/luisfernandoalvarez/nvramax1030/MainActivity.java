package de.luisfernandoalvarez.nvramax1030;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;


import java.io.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
	
	public void onCopiarNVRAMButtonClick(View view) 
    {
    	// Copia la NVRAM (Hardware IMEI)
		try {
			// Comando antiguo, archivado para la historia.
			Runtime.getRuntime().exec("su -c dd if=/dev/block/mmcblk0p17 of=/sdcard/nvdata-ax1055.img count=1 bs=33554432");
			// Copiar Hardware IMEI
			Runtime.getRuntime().exec("su -c dd if=/dev/block/mmcblk0p2 of=/sdcard/nvram-ax1055.img count=1 bs=5242880");
		} catch (IOException e) {
		} 
    	Toast.makeText(this, "Hardware IMEI copiado a /sdcard con éxito", Toast.LENGTH_SHORT).show();
	}
	
	public void onRestaurarNVRAMButtonClick(View view) 
    {
    	// Restaurar NVRAM
		try {
			// Limpieza de los archivos de SOFT IMEI generados corruptamente.
			Runtime.getRuntime().exec("su -c rm -r /nvdata/*");
			Runtime.getRuntime().exec("su -c rm /data/nvram");
			// Runtime.getRuntime().exec("su -c rmdir /nvdata/*");
			// NVRAM CORRUPTO GENERADO POR EL SISTEMA EN folder /data/nvram
			Runtime.getRuntime().exec("su -c rm -r /data/nvram/*");
			Runtime.getRuntime().exec("su -c rmdir /data/nvram");
			// COMANDO PARA LA HISTORIA
			Runtime.getRuntime().exec("su -c dd if=/sdcard/nvdata-ax1055.img of=/dev/block/mmcblk0p17");
			// Flashear region binaria a la particion No. 17, que el ax1055 esta el Hardware IMEI.
			Runtime.getRuntime().exec("su -c dd if=/sdcard/nvram-ax1055.img of=/dev/block/mmcblk0p2");
		} catch (IOException e) {
		} 
    	Toast.makeText(this, "Hardware IMEI Restaurado desde /sdcard con éxito", Toast.LENGTH_SHORT).show();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate main_menu.xml 
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.mainMenuAcerca:
				Toast.makeText(this, "Desarrollador: Luis Fernando Alvarez Yanes", Toast.LENGTH_LONG).show();
				Toast.makeText(this, "Guatemala, marzo de 2016", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.mainMenuReiniciar:
				try {
					Runtime.getRuntime().exec("su -c reboot");
				} catch (IOException e) {
				}
				return true;
			case R.id.mainMenuSalir:
				Toast.makeText(this, "¡Gracias por usar mis proyectos!", Toast.LENGTH_LONG).show();
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
