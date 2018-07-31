package bildkompressor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JPEGCompression implements Runnable
{
	private long startTime, endTime, completeTime;
	private InputStream is = null;
	private OutputStream os = null;
	private BufferedImage image = null;
	private Iterator<ImageWriter> writers = null;
	private ImageWriter writer = null;
	private ImageOutputStream ios = null;
	private ImageWriteParam param = null;
	private final JProgressBar progressBar;
	private final JLabel outputL;
	private final JButton button;
	private final JSlider slider;

	public JPEGCompression(final Gui gui) throws Exception
	{
		super();
		progressBar = gui.getProgressBar();
		outputL = gui.getLblNewLabel();
		button = gui.getBtnStart();
		slider = gui.getSlider();
		button.setEnabled(false);
		slider.setEnabled(false);
	}

	public JFileChooser getChooser(final String path)
	{
		final FileFilter filter = new FileNameExtensionFilter("Bilder", "gif", "png", "jpg");
		final JFileChooser chooser = new JFileChooser(path);
		chooser.addChoosableFileFilter(filter);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);
		return chooser;
	}

	@Override
	public void run()
	{
		startTime = 0L;
		endTime = 0L;
		completeTime = 0L;

		final String path = "P:\\Marketing\\Bilder\\Spatenstich\\";
		final String newPath = path + "Compressed";

		final JFileChooser chooser = getChooser(path);
		final int open = chooser.showOpenDialog(null);
		final int scal = JOptionPane.showConfirmDialog(null, "Sollen die Bilder noch skaliert werden?", "",
				JOptionPane.YES_NO_OPTION);

		if (open == JFileChooser.APPROVE_OPTION)
		{
			startTime = System.currentTimeMillis();
			// System.out.print("Start Compressing... ");
			setOutput("Start Compressing... ");

			final File[] allFiles = chooser.getSelectedFiles();
			progressBar.setMaximum(allFiles.length);

			final File newPlace = new File(newPath);
			if (!newPlace.exists()) if (newPlace.mkdir())
			{
			}
			else
				throw new SecurityException("Failed to create directory: " + newPath);

			for (int i = 0; i < allFiles.length; i++)
			{
				// System.out.print(i + 1 + " von " + allFiles.length + "
				// Bildern wird bearbeitet...");
				setOutput(i + 1 + " von " + allFiles.length + " Bildern wird bearbeitet...");
				final File imageFile = allFiles[i];
				final File compressedImageFile = new File(newPlace + "\\" + allFiles[i].getName() + "_Compressed.jpg");

				try
				{
					is = new FileInputStream(imageFile);
					os = new FileOutputStream(compressedImageFile);
				}
				catch (final FileNotFoundException e)
				{
					e.printStackTrace();
				}
				float quality = 0.4f;
				quality = slider.getValue() / 100.0f;
				// System.out.println(quality);

				// create a BufferedImage as the result of decoding the supplied
				// InputStream
				try
				{
					image = ImageIO.read(is);
					if (scal == JOptionPane.YES_OPTION) image = scaleImage(1000, (1000 / 3) * 2);
				}
				catch (final IOException e)
				{
					setOutput(e.getMessage());
					e.printStackTrace();
				}

				// get all image writers for JPG format
				writers = ImageIO.getImageWritersByFormatName("jpg");
				if (!writers.hasNext()) throw new IllegalStateException("No writers found");
				writer = writers.next();
				try
				{
					ios = ImageIO.createImageOutputStream(os);
				}
				catch (final IOException e)
				{
					setOutput(e.getMessage());
					e.printStackTrace();
				}
				writer.setOutput(ios);

				param = writer.getDefaultWriteParam();

				// compress to a given quality
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(quality);

				// appends a complete image stream containing a single image and
				// associated stream and image metadata and thumbnails to the
				// output
				try
				{
					writer.write(null, new IIOImage(image, null, null), param);
				}
				catch (final IOException e)
				{
					setOutput(e.getMessage());
					e.printStackTrace();
				}

				try
				{
					is.close();
					os.close();
					ios.close();
				}
				catch (final IOException e)
				{
					setOutput(e.getMessage());
					e.printStackTrace();
				}
				writer.dispose();

				progressBar.setValue(1 + i);
			}

			endTime = System.currentTimeMillis();
			completeTime = endTime - startTime;
			button.setEnabled(true);
			slider.setEnabled(true);
			// System.out.println("Completed " + allFiles.length + " files after
			// " + (completeTime / 1000) + " seconds.");
			setOutput("Completed " + allFiles.length + " files after " + (completeTime / 1000) + " seconds.");
		}
	}

	private BufferedImage scaleImage(final int width, final int height)
	{
		BufferedImage bimage;
		if (image.getHeight() < image.getWidth())
		{
			bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// Draw the image on to the buffered image
			final Graphics2D bGr = bimage.createGraphics();
			bGr.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			bGr.dispose();
		}
		else
		{
			bimage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
			final Graphics2D bGr = bimage.createGraphics();
			bGr.drawImage(image.getScaledInstance(height, width, Image.SCALE_SMOOTH), 0, 0, null);
			bGr.dispose();
		}

		return bimage;
	}

	public void setOutput(final String output)
	{
		outputL.setText(output);
	}
}