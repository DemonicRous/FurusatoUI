"""Prepare a generated 2:1 panorama as a seamless 4K equirectangular master."""

import argparse
from pathlib import Path

from PIL import Image


MASTER_SIZE = (4096, 2048)
SEAM_BLEND = 256


def smoothstep(value: float) -> float:
    return value * value * (3.0 - 2.0 * value)


def make_seamless(source: Image.Image) -> Image.Image:
    image = source.convert("RGB").resize(MASTER_SIZE, Image.Resampling.LANCZOS)
    pixels = image.load()
    width, height = image.size

    # Symmetrically blend matching pixels on both sides of the wrap boundary.
    # The first and last columns become identical while the correction fades
    # smoothly into the untouched panorama.
    for distance in range(SEAM_BLEND):
        strength = 1.0 - smoothstep(distance / float(SEAM_BLEND - 1))
        left_x = distance
        right_x = width - 1 - distance
        for y in range(height):
            left = pixels[left_x, y]
            right = pixels[right_x, y]
            average = tuple((left[channel] + right[channel]) // 2 for channel in range(3))
            pixels[left_x, y] = tuple(
                round(left[channel] * (1.0 - strength) + average[channel] * strength)
                for channel in range(3))
            pixels[right_x, y] = tuple(
                round(right[channel] * (1.0 - strength) + average[channel] * strength)
                for channel in range(3))
    return image


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("input", type=Path)
    parser.add_argument("output", type=Path)
    args = parser.parse_args()
    args.output.parent.mkdir(parents=True, exist_ok=True)
    with Image.open(args.input) as source:
        make_seamless(source).save(args.output, optimize=True)


if __name__ == "__main__":
    main()
