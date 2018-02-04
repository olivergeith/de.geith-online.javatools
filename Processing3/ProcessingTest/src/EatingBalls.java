import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class EatingBalls extends PApplet {

	static int w = 1024;
	static int h = 768;

	int anzBalls = 50;
	List<Ball> balls = new ArrayList<>();

	List<Ball> toDelete = new ArrayList<>();
	List<Ball> toAdd = new ArrayList<>();

	public static void main(final String[] args) {
		PApplet.main("EatingBalls");
	}

	@Override
	public void mouseClicked(final MouseEvent event) {
		super.mouseClicked(event);
		System.out.println("Button = " + event.getButton());
		if (event.getButton() == 37) {
			balls.add(new Ball(event.getX(), event.getY()));
		} else {
			for (final Ball b : balls) {
				System.out.println(event.getX() + "-" + event.getY());
				if (b.isTouched(event.getX(), event.getY())) {
					toDelete.add(b);
				}
			}
		}
	}

	@Override
	public void settings() {
		size(1024, 768);
	}

	@Override
	public void setup() {
		frameRate(50);
		for (int i = 0; i < anzBalls; i++) {
			balls.add(new Ball());
		}
		// balls.add(new Ball(150));
	}

	@Override
	public void draw() {
		background(32);

		calcTouches();
		// calcNewCilds();

		for (final Ball del : toDelete) {
			balls.remove(del);
		}
		toDelete.clear();
		balls.addAll(toAdd);
		toAdd.clear();
		for (final Ball ball : balls) {
			ball.draw();
		}
	}

	private void calcNewCilds() {
		for (int i = 0; i < balls.size(); i++) {
			final Ball ball = balls.get(i);
			if (ball.rad > 25) {
				toDelete.add(ball);
				if (balls.size() < 500) {
					final int newBallCount = (int) random(4, 20);
					for (int y = 0; y < newBallCount; y++) {
						toAdd.add(new Ball());
					}
				}
			}

		}
	}

	private void calcTouches() {
		for (int i = 0; i < balls.size(); i++) {
			final Ball ball = balls.get(i);

			for (int j = i + 1; j < balls.size(); j++) {
				final Ball other = balls.get(j);
				if (!other.equals(ball) && ball.isTouched(other)) {
					ball.dx = ball.getRandomDeltaCollusion(5);
					ball.dy = -ball.getRandomDeltaCollusion(5);
					if (ball.rad > other.rad) {
						toDelete.add(other);
						ball.rad = ball.rad + other.rad / 2;
					} else {
						toDelete.add(ball);
						other.rad = other.rad + ball.rad / 2;
					}
					// toAdd.add(this);
					other.dx = other.getRandomDeltaCollusion(5);
					other.dy = -other.getRandomDeltaCollusion(5);
				}
			}
		}
	}

	private class Ball {
		int rad = (int) random(5, 10);
		int x = (int) random(rad, w - rad);
		int y = (int) random(rad, h - rad);;
		int dx = getRandomDeltaCollusion(3);
		int dy = getRandomDeltaCollusion(3);
		int r = 254;
		int g = 128;
		int b = 1;
		int dr = 1;
		int dg = 1;
		int db = 1;

		public Ball() {
			randomizeColor();
		}

		public Ball(final int radius) {
			randomizeColor();
			rad = radius;
		}

		public Ball(final int x, final int y) {
			randomizeColor();
			this.x = x;
			this.y = y;
		}

		private int getRandomDelta(final int max) {
			return (int) random(0, max);
		}

		private int getRandomDeltaCollusion(final int max) {
			return (int) random(-max, max);
		}

		private int fixPos(final int pos, final int max) {
			if (pos < 0) {
				return 0;
			}
			if (pos > max) {
				return max;
			}
			return pos;
		}

		private void randomizeColor() {
			r = (int) random(255);
			g = (int) random(255);
			b = (int) random(255);
		}

		private void changeColor() {
			// farben
			r = r + dr;
			g = g + dg;
			b = b + db;
			if (r > 254 || r < 1) {
				dr = dr * -1;
			}
			if (g > 254 || g < 1) {
				dg = dg * -1;
			}
			if (b > 254 || b < 1) {
				db = db * -1;
			}

		}

		public boolean isTouched(final int tx, final int ty) {
			if (tx > x - rad //
					&& tx < x + rad //
					&& ty > y - rad //
					&& ty < y + rad) {
				System.out.println("Toch ");
				return true;
			}
			return false;
		}

		public boolean isTouched(final Ball other) {
			if (x + rad > other.x - other.rad && x - rad < other.x + other.rad && y + rad > other.y - other.rad && y - rad < other.y + other.rad) {
				return true;
			}
			return false;
		}

		// public boolean checkerOtherBalls() {
		// final int abstrandVomRand = 3 * rad;
		// for (final Ball other : balls) {
		// if (x > abstrandVomRand && x < w - abstrandVomRand && y > abstrandVomRand && y < h - abstrandVomRand) {
		// if (!other.equals(this) && isTouched(other)) {
		// dx = getRandomDeltaCollusion(5);
		// dy = -getRandomDeltaCollusion(5);
		// toDelete.add(other);
		// // toAdd.add(this);
		// rad = rad + other.rad;
		// other.dx = getRandomDeltaCollusion(5);
		// other.dy = -getRandomDeltaCollusion(5);
		// return true;
		// }
		// }
		// }
		// return false;
		// }

		public void draw() {
			fill(r, g, b);
			stroke(r, g, b);
			if (x >= w - rad) {
				dx = -getRandomDelta(4);
				// randomizeColor();
			}
			if (x <= rad) {
				dx = getRandomDelta(4);
				// randomizeColor();
			}
			if (y >= h - rad) {
				dy = -getRandomDelta(4);
				// randomizeColor();
			}
			if (y <= rad) {
				dy = getRandomDelta(4);
				// randomizeColor();
			}
			// checkerOtherBalls();
			x = fixPos(x + dx, w);
			y = fixPos(y + dy, h);
			changeColor();
			// ellipseMode(RADIUS);
			// ellipse(x, y, rad, rad);
			rectMode(RADIUS);
			rect(x, y, rad, rad, 2);
			// text(x + "-" + y, x + rad + 5, y);
		}
	}

}
