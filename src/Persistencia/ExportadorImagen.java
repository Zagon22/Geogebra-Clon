package Persistencia;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public final class ExportadorImagen {
    private ExportadorImagen () {};

    public static File exportarPNG(BufferedImage img, File destino) throws IOException {
        File f = rutaConExtension(destino, "png");
        if(!ImageIO.write(img, "png", f)) {
            throw new IOException("No hay escritor PNG disponible");
        }

        return f;
    }

    public static File rutaConExtension(File f, String ext) {
        String nombre = f.getName().toLowerCase();
        return nombre.endsWith("." + ext)
            ? f
            : new File(f.getAbsolutePath() + "." + ext);
    }
}
