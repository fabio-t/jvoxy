package com.github.fabioticconi.libgdxtest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage.*;

/**
 * Author: Fabio Ticconi
 * Date: 11/03/18
 */
public enum Block
{
    GRASS("dirt", 0, 0),
    STONE("stone", 1, 0),
    PLANK("plank", 4, 0),
    GLASS("glass", 1, 3);

    public static int TEXTURE_WIDTH = 16;

    String name;
    int    x, y;

    TextureRegion texture = null;

    Model model;

    Block(final String name, final int x, final int y)
    {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public static void init(final float cubeSize)
    {
        final Texture      allTextures  = new Texture("textures.png");
        final ModelBuilder modelBuilder = new ModelBuilder();

        for (final Block type : values())
        {
            type.texture = new TextureRegion(allTextures,
                                             type.x * TEXTURE_WIDTH,
                                             type.y * TEXTURE_WIDTH,
                                             TEXTURE_WIDTH,
                                             TEXTURE_WIDTH);

            final int attr = Position | Normal | TextureCoordinates;

            final Material material = new Material(TextureAttribute.createDiffuse(type.texture));

            type.model = modelBuilder.createBox(cubeSize, cubeSize, cubeSize, material, attr);
        }
    }

    public ModelInstance make(final float x, final float y, final float z)
    {
        final ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(x, y, z);

        return instance;
    }
}
