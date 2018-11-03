package primitivos;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.scene.BoundsAccessor;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;

/**
 * Representa um ponto gr�fico
 *
 * @author Julio Arakaki
 * @version v1.0 Data: 2018/05/06
 */
public class PontoGr extends Ponto {
	private Color cor; // cor do ponto
	private String nomeP; // nome do ponto
	private Color corNomeP; // cor do nome
	private int diametro = 1; // diametro do ponto, default = 1

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 *
	 * @param x
	 *            coordenada x
	 * @param y
	 *            coordenada y
	 * @param cor
	 *            cor do ponto a ser construido
	 * @param diametro
	 *            diametro do ponto
	 */
	public PontoGr(int x, int y, Color cor) {
		super((double) x, (double) y);
		setCor(cor);
		setCorNomeP(Color.BLACK);
		setNomeP("");
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 *
	 * @param x
	 *            coordenada x
	 * @param y
	 *            coordenada y
	 * @param cor
	 *            cor do ponto a ser construido
	 * @param diametro
	 *            diametro do ponto
	 */
	public PontoGr(int x, int y, Color cor, int diametro) {
		super((double) x, (double) y);
		setCor(cor);
		setCorNomeP(Color.BLACK);
		setNomeP("");
		setDiametro(diametro);
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 *
	 * @param x
	 *            coordenada x
	 * @param y
	 *            coordenada y
	 * @param cor
	 *            cor do ponto a ser construido
	 * @param nomeP
	 *            nome do ponto
	 * @param diametro
	 *            diametro do ponto
	 */
	public PontoGr(int x, int y, Color cor, String nomeP, int diametro) {
		super((double) x, (double) y);
		setCor(cor);
		setCorNomeP(Color.BLACK);
		setNomeP(nomeP);
		setDiametro(diametro);
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 *
	 * @param x
	 *            coordenada x
	 * @param y
	 *            coordenada y
	 * @param cor
	 *            cor do ponto a ser construido
	 * @param nomeP
	 *            nome do ponto
	 * @param diametro
	 *            diametro do ponto
	 */
	public PontoGr(int x, int y, Color cor, String nomeP) {
		super((double) x, (double) y);
		setCor(cor);
		setCorNomeP(Color.BLACK);
		setNomeP(nomeP);
	}

	/**
	 * Constroi um ponto baseado em outro ponto grafico
	 *
	 * @param pg
	 *            outro ponto
	 * @param cor
	 *            cor do ponto a ser construido
	 * @param diametro
	 *            diametro do ponto
	 */
	public PontoGr(PontoGr pg, Color cor, int diametro) {
		super(pg.getx(), pg.gety());
		setCor(cor);
		setCorNomeP(Color.BLACK);
		setNomeP("");
		setDiametro(diametro);
	}

	/**
	 * Constroi um ponto na posicao 0, 0 com diametro e cor Black
	 *
	 * @param diametro
	 */
	public PontoGr(int x, int y) {
		super((double) x, (double) y);
		setCor(Color.BLACK);
		setCorNomeP(Color.BLACK);
		setNomeP("");
	}

	/**
	 * Constroi um ponto na posicao 0, 0 com diametro e cor Black
	 *
	 * @param diametro
	 */
	public PontoGr(int diametro) {
		super(0, 0);
		setCor(Color.BLACK);
		setCorNomeP(Color.BLACK);
		setNomeP("");
		setDiametro(diametro);

	}

	private Color getCor() {
		return cor;
	}

	private void setCor(Color cor) {
		this.cor = cor;
	}

	private String getStr() {
		return nomeP;
	}

	private Color getCorStr() {
		return corNomeP;
	}

	private int getDiametro() {
		return diametro;
	}

	private void setDiametro(int diametro) {
		this.diametro = diametro;
	}

	private void setCorNomeP(Color corNomeP) {
		this.corNomeP = corNomeP;
	}

	private void setNomeP(String nomeP) {
		this.nomeP = nomeP;
	}

	/**
	 * desenha um ponto utilizando o oval
	 *
	 * @param g
	 *            contexto grafico
	 */
	public void desenharPonto(GraphicsContext g) {
		// desenha ponto como um oval
		g.setFill(getCor());
		g.fillOval((int) getx() - (getDiametro() / 2), (int) gety() - (getDiametro() / 2), getDiametro(),
				getDiametro());

		// desenha nome do ponto
		g.setFill(getCorStr());
		g.strokeText(getStr(), (int) getx() + getDiametro(), (int) gety());
		
	}
}
