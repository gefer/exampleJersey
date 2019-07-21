package com.mycompany.exampleweb.controller;

import com.mycompany.exampleweb.dao.CidadeDao;
import com.mycompany.exampleweb.entities.Cidade;
import com.mycompany.exampleweb.util.Charset;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/cidades")
public class CidadeREST {

    private CidadeDao cidadeDao;

    public CidadeREST() {
        this.cidadeDao = new CidadeDao();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + Charset.UTF_8)
    public void saveOrEdit(Cidade cidade) {
        cidadeDao.edit(cidade);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + Charset.UTF_8)
    public List<Cidade> findAll() {

        return cidadeDao.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + Charset.UTF_8)
    public Cidade findByIdIbge(@PathParam("id") Integer id) {

        return cidadeDao.findByIdIbge(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + Charset.UTF_8)
    public List<String> findByUf(@PathParam("uf") String uf) {

        return cidadeDao.findCityNameByUf(uf);
    }

    @POST
    @Path("/csv")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response uploadCsvFile(@FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {

        int count = 0;
        List<String[]> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream))) {

            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }

        return Response.ok("Data uploaded successfully !!").build();
    }

}
