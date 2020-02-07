package com.druffel.foodservice.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.model.UploadedFile;

import com.druffel.foodservice.entity.Configuration;
import com.druffel.foodservice.service.ConfigurationService;

@ViewScoped
@Named
public class SettingsManagedBean implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final Logger logger = Logger.getLogger(SettingsManagedBean.class);
    
    @Inject
    private ConfigurationService configurationService;
    
    private String bankEmailAdress;
    
    private Supplier<Configuration> bankEmailConfiguration;
    
    private UploadedFile buschePlan;
    
    @PostConstruct
    public void init()
    {
        bankEmailConfiguration = () -> 
        {
            return configurationService.getConfigurationByName(ConfigurationKeys.BANK_EMAIL);
        };
  
        
        bankEmailAdress = bankEmailConfiguration.get().getConfvalue();
    }

    public void onSave()
    {
       configurationService.remove(bankEmailConfiguration.get());
       configurationService.create(new Configuration(ConfigurationKeys.BANK_EMAIL, bankEmailAdress));
    
       //upload file
       if(buschePlan != null)
       {
           try
        {
            InputStream input = buschePlan.getInputstream();
            Path saveFolder = Paths.get(System.getProperty("jboss.home.dir") + "/var/images/");
            
            String filename = "/busche";
            String extension = "pdf";
            
            File plan = new File(saveFolder+filename+"."+extension);
            Path file = plan.toPath();
            try 
            {
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                logger.info("saved in: " + file.toString());
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       }
    }
    
    public String getBankEmailAdress()
    {
        return bankEmailAdress;
    }

    public void setBankEmailAdress(String bankEmailAdress)
    {
        this.bankEmailAdress = bankEmailAdress;
    }

    public UploadedFile getBuschePlan()
    {
        return buschePlan;
    }

    public void setBuschePlan(UploadedFile buschePlan)
    {
        this.buschePlan = buschePlan;
    }
    
}
