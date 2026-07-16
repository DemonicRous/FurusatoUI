"""Convert Furusato source art into Minecraft 1.12.2 title-screen textures."""

from math import atan2, asin, pi, sqrt
from pathlib import Path

from PIL import Image


ROOT = Path(__file__).resolve().parents[1]
PANORAMA_SOURCE = ROOT / "art" / "furusato_panorama_equirectangular.png"
PANORAMA_OUTPUT = ROOT / "src/main/resources/assets/minecraft/textures/gui/title/background"
FACE_SIZE = 512


def direction(face: int, u: float, v: float) -> tuple[float, float, float]:
    if face == 0:  # front
        return u, -v, 1.0
    if face == 1:  # right
        return 1.0, -v, -u
    if face == 2:  # back
        return -u, -v, -1.0
    if face == 3:  # left
        return -1.0, -v, u
    if face == 4:  # top
        return u, 1.0, v
    return u, -1.0, -v  # bottom


def build_panorama() -> None:
    source = Image.open(PANORAMA_SOURCE).convert("RGB")
    source_pixels = source.load()
    PANORAMA_OUTPUT.mkdir(parents=True, exist_ok=True)

    for face in range(6):
        output = Image.new("RGB", (FACE_SIZE, FACE_SIZE))
        pixels = output.load()
        for py in range(FACE_SIZE):
            v = 2.0 * (py + 0.5) / FACE_SIZE - 1.0
            for px in range(FACE_SIZE):
                u = 2.0 * (px + 0.5) / FACE_SIZE - 1.0
                x, y, z = direction(face, u, v)
                length = sqrt(x * x + y * y + z * z)
                longitude = atan2(x, z)
                latitude = asin(y / length)
                sx = int((longitude / (2.0 * pi) + 0.5) * source.width) % source.width
                sy = max(0, min(source.height - 1,
                        int((0.5 - latitude / pi) * source.height)))
                pixels[px, py] = source_pixels[sx, sy]
        output.save(PANORAMA_OUTPUT / f"panorama_{face}.png", optimize=True)


if __name__ == "__main__":
    build_panorama()
