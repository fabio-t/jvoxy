package com.github.fabioticconi.libgdxtest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT;
import static com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute.AmbientLight;

public class Game extends ApplicationAdapter
{
    public Environment           environment;
    public PerspectiveCamera     cam;
    public CameraInputController camController;
    public ModelBatch            modelBatch;
    public Array<ModelInstance> instances = new Array<ModelInstance>();

    @Override
    public void create()
    {
        environment = new Environment();
        environment.set(new ColorAttribute(AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 200f, 200f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 1000f;
        cam.update();

        Block.init(5f);

        final int minX = 0, maxX = 30;
        final int minZ = 0, maxZ = 30;
        final float centreX = (maxX - minX) / 2f;
        final float centreZ = (maxZ - minZ) / 2f;

        for (int x = minX; x < maxX; x++)
        {
            for (int z = minZ; z < maxZ; z++)
            {
                final float y = centreX - Math.max(Math.abs(x - centreX), Math.abs(z - centreZ));

                for (int yy = 0; yy < y; yy++)
                {
                    instances.add(Block.DIRT_GRASS.make(x*5f, yy*5f, z*5f));
                }
            }
        }

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void render()
    {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    @Override
    public void dispose()
    {
        modelBatch.dispose();
    }

    @Override
    public void resize(final int width, final int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }
}
