/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur.administration;

import entities.Candidat;
import entities.SecteurActivite;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import services.CandidatFacade;
import services.CvFacade;
import services.MembreFacade;
import services.OffreFacade;
import services.SecteurActiviteFacade;

/**
 *
 * @author AmineD
 */
@Named
@ViewScoped
public class DashboardControleur implements Serializable{
    private LineChartModel lineInscriptions;
    private LineChartModel lineNbrAnnonces;
    private BarChartModel barSecteurActiviteOffre;
    private BarChartModel barSecteurActiviteRecherche;
    private PieChartModel pieGenreMembreInscris;
    private PieChartModel pieGenreMembreEnRechercheEmploie;
    
    private ArrayList<SecteurActivite> listeSecteurs;
    
    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
    private Date date0,date1,date2,date3,date4,date5,date6,date7; //7 derniers jours    ,0=maintenant, 1=aujourd'hui ,2 =hier ..ect
    
    @EJB
    private MembreFacade membreFacade;
    @EJB
    private CvFacade cvFacade;
    @EJB
    private CandidatFacade candidatFacade;
    @EJB
    private OffreFacade offreFacade;
    @EJB
    private SecteurActiviteFacade secteurActiviteFacade;
   
    
    @PostConstruct
    public void init(){
                
        try{
            listeSecteurs=secteurActiviteFacade.findSecteursActivite();
            System.out.println("size liste secteyrs activité "+listeSecteurs.size());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        initDate();
        createLineModels();
        createBarModels();
        createPieGenreMembreInscris();
        createPieGenreMembreEnRechercheEmploie();

    }
    
    public Integer nbreCvs(){
        return cvFacade.count();
    }
    
    public Integer nbreCvsActifs(){
        return candidatFacade.countActiveCv();
    }
    
    public Integer nbreOffres(){
        return offreFacade.count();
    }
    public Integer nbreMembres(){
        return membreFacade.count();
    }
    
    public long nbreOffresAujourdhui(){
        return offreFacade.countTodayOffers();
    }
    public long nbreMembresInscrisAujourdhui(){
        return membreFacade.countTodayRegistredMembers();
    }
    public long nbreMembresConnectesAujourdhui(){
        return membreFacade.countTodayLoggedInMembers();
    }
    private void initDate(){
        date0=new Date(); //today at this time 
        date1=new Date(today.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000); //today at 00:00
        date2=new Date(today.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000); //yesterday at 00:00
        date3=new Date(today.minusDays(2).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
        date4=new Date(today.minusDays(3).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
        date5=new Date(today.minusDays(4).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
        date6=new Date(today.minusDays(5).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
        date7=new Date(today.minusDays(6).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
    }
    
    
    private void createLineModels() {
        lineInscriptions = initNbreAnnonces();
        lineInscriptions.setTitle("Nombre d'offres d'emploi durant les 7 derniers jours");
        lineInscriptions.setLegendPosition("e");
        lineInscriptions.setAnimate(true);
        lineInscriptions.getAxes().put(AxisType.X, new CategoryAxis("Date"));
        
        Axis yAxis = lineInscriptions.getAxis(AxisType.Y);
        yAxis.setLabel("Nombre d'offres");
        yAxis.setMin(0);
        
        
        lineNbrAnnonces = initLinearNbreInscriptions();
        lineNbrAnnonces.setTitle("Nombre d'inscriptions durant les 7 derniers jours");
        lineNbrAnnonces.setLegendPosition("e");
        lineNbrAnnonces.setShowPointLabels(true);
        lineNbrAnnonces.getAxes().put(AxisType.X, new CategoryAxis("Date"));
        lineNbrAnnonces.setAnimate(true);
        
        yAxis = lineNbrAnnonces.getAxis(AxisType.Y);
        yAxis.setLabel("Nombre d'inscriptions");
        yAxis.setMin(0);
        }
        
    private void createBarModels() {
        barSecteurActiviteOffre = initBarSecteursActiviteOffre();
         
        barSecteurActiviteOffre.setTitle("Nombres d'offres d'emploi par secteur d'activité");
        barSecteurActiviteOffre.setLegendPosition("ne");
        barSecteurActiviteOffre.setAnimate(true);
        
        Axis xAxis =barSecteurActiviteOffre.getAxis(AxisType.X);
        xAxis.setLabel("Liste des secteurs");
         
        Axis yAxis = barSecteurActiviteOffre.getAxis(AxisType.Y);
        yAxis.setLabel("Nombres d'offres");
        yAxis.setMin(0);  
        
        
        barSecteurActiviteRecherche = initBarSecteursActiviteEnRechercheEmploie();
         
        barSecteurActiviteRecherche.setTitle("Nombres de personnes en recherche d'emploie par secteur d'activité");
        barSecteurActiviteRecherche.setLegendPosition("ne");
        barSecteurActiviteRecherche.setAnimate(true);
        
        xAxis =barSecteurActiviteRecherche.getAxis(AxisType.X);
        xAxis.setLabel("Liste des secteurs");
         
        yAxis = barSecteurActiviteRecherche.getAxis(AxisType.Y);
        yAxis.setLabel("Nombres de personnes");
        yAxis.setMin(0); 

    }
        
    private void createPieGenreMembreInscris () {
        pieGenreMembreInscris = new PieChartModel();
         
        pieGenreMembreInscris.set("Homme", 4);
        pieGenreMembreInscris.set("Femme", 1);
        pieGenreMembreInscris.setShowDataLabels(true);
        pieGenreMembreInscris.setTitle("Candidats inscris par genre");
        pieGenreMembreInscris.setLegendPosition("w");
        pieGenreMembreInscris.setSeriesColors("1DC7EA,FB404B");
        pieGenreMembreInscris.setExtender("customExtender");
    }  
    
    private void createPieGenreMembreEnRechercheEmploie () {
        pieGenreMembreEnRechercheEmploie = new PieChartModel();
         
        pieGenreMembreEnRechercheEmploie.set("Homme", 3);
        pieGenreMembreEnRechercheEmploie.set("Femme", 1);
         
        pieGenreMembreEnRechercheEmploie.setTitle("Candidats en recherche d'emploi par genre");
        pieGenreMembreEnRechercheEmploie.setLegendPosition("e");
        pieGenreMembreEnRechercheEmploie.setFill(false);
        pieGenreMembreEnRechercheEmploie.setShowDataLabels(true);
        pieGenreMembreEnRechercheEmploie.setDiameter(150);
        pieGenreMembreEnRechercheEmploie.setSeriesColors("1DC7EA,FB404B");
        pieGenreMembreEnRechercheEmploie.setExtender("customExtender");

    }  
        
    private LineChartModel initNbreAnnonces() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("annonces");
        Date dd=new Date();
        series1.set(LocalDate.now().minusDays(6).format(formatter), offreFacade.countOffersByMargeDateCreation(date7, date6));
        series1.set(LocalDate.now().minusDays(5).format(formatter), offreFacade.countOffersByMargeDateCreation(date6, date5));
        series1.set(LocalDate.now().minusDays(4).format(formatter), offreFacade.countOffersByMargeDateCreation(date5, date4));
        series1.set(LocalDate.now().minusDays(3).format(formatter), offreFacade.countOffersByMargeDateCreation(date4, date3));
        series1.set(LocalDate.now().minusDays(2).format(formatter), offreFacade.countOffersByMargeDateCreation(date3, date2));
        series1.set(LocalDate.now().minusDays(1).format(formatter), offreFacade.countOffersByMargeDateCreation(date2, date1));
        series1.set(LocalDate.now().format(formatter), offreFacade.countOffersByMargeDateCreation(date1,date0));

        model.addSeries(series1);
        
        model.setSeriesColors("58BA27");
        model.setExtender("customExtender");
        return model;
    }
    
    
       private LineChartModel initLinearNbreInscriptions() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries recruteurs = new ChartSeries();
        recruteurs.setLabel("Recruteurs");
        recruteurs.set(LocalDate.now().minusDays(6).format(formatter), membreFacade.countRecruteursByMargeDateCreation(date7, date6));
        recruteurs.set(LocalDate.now().minusDays(5).format(formatter), membreFacade.countRecruteursByMargeDateCreation(date6, date5));
        recruteurs.set(LocalDate.now().minusDays(4).format(formatter), membreFacade.countRecruteursByMargeDateCreation(date5, date4));
        recruteurs.set(LocalDate.now().minusDays(3).format(formatter), membreFacade.countRecruteursByMargeDateCreation(date4, date3));
        recruteurs.set(LocalDate.now().minusDays(2).format(formatter), membreFacade.countRecruteursByMargeDateCreation(date3, date2));
        recruteurs.set(LocalDate.now().minusDays(1).format(formatter), membreFacade.countRecruteursByMargeDateCreation(date2, date1)); //yesterday
        recruteurs.set(LocalDate.now().format(formatter), membreFacade.countRecruteursByMargeDateCreation(date1, date0)); //today





 
 
        ChartSeries candidats = new ChartSeries();
        candidats.setLabel("Candidats");
        candidats.set(LocalDate.now().minusDays(6).format(formatter), membreFacade.countCandidatsByMargeDateCreation(date7, date6));
        candidats.set(LocalDate.now().minusDays(5).format(formatter), membreFacade.countCandidatsByMargeDateCreation(date6, date5));
        candidats.set(LocalDate.now().minusDays(4).format(formatter), membreFacade.countCandidatsByMargeDateCreation(date5, date4));
        candidats.set(LocalDate.now().minusDays(3).format(formatter),  membreFacade.countCandidatsByMargeDateCreation(date4, date3));
        candidats.set(LocalDate.now().minusDays(2).format(formatter), membreFacade.countCandidatsByMargeDateCreation(date3, date2));
        candidats.set(LocalDate.now().minusDays(1).format(formatter),membreFacade.countCandidatsByMargeDateCreation(date2, date1)); //yesterday
        candidats.set(LocalDate.now().format(formatter), membreFacade.countCandidatsByMargeDateCreation(date1, date0)); //today






 
        model.addSeries(recruteurs);
        model.addSeries(candidats);
        
        model.setSeriesColors("3F51B5,f9b84a");
        model.setExtender("customExtender");
         
        return model;
    }
    
    private BarChartModel initBarSecteursActiviteEnRechercheEmploie() {
        BarChartModel model = new BarChartModel();
        
        if(listeSecteurs!=null)
            for(int i=0;i<listeSecteurs.size();i++){
               ChartSeries secteur = new ChartSeries(); 
               secteur.setLabel(listeSecteurs.get(i).getNomSecteurActivite());
                System.out.println("-----SECTEUR : "+listeSecteurs.get(i).getNomSecteurActivite());
               secteur.set("Toutes les années", cvFacade.countCandidatEnRechercheBySecteur(listeSecteurs.get(i)));
               model.addSeries(secteur);
            }
      
                
        model.setSeriesColors("58BA27,FFCC33,AA3F84,1dc7ea,E02D53,26A2ED,#41C572");
        model.setExtender("customExtender");
         
        return model;
    }
    
    
    private BarChartModel initBarSecteursActiviteOffre() {
        BarChartModel model = new BarChartModel();
 
        if(listeSecteurs!=null)
            for(int i=0;i<listeSecteurs.size();i++){
               ChartSeries secteur = new ChartSeries(); 
               secteur.setLabel(listeSecteurs.get(i).getNomSecteurActivite());
                System.out.println("-----SECTEUR : "+listeSecteurs.get(i).getNomSecteurActivite());
               secteur.set("Toutes les années", offreFacade.countOffersBySecteur(listeSecteurs.get(i)));
               model.addSeries(secteur);
            }
        
                
        model.setSeriesColors("58BA27,FFCC33,AA3F84,1dc7ea,E02D53,26A2ED,#41C572");
        model.setExtender("customExtender");
         
        return model;
    }
    
    

    public LineChartModel getLineInscriptions() {
        return lineInscriptions;
    }

    public void setLineInscriptions(LineChartModel lineInscriptions) {
        this.lineInscriptions = lineInscriptions;
    }

    public LineChartModel getLineNbrAnnonces() {
        return lineNbrAnnonces;
    }

    public void setLineNbrAnnonces(LineChartModel lineNbrAnnonces) {
        this.lineNbrAnnonces = lineNbrAnnonces;
    }

    public BarChartModel getBarSecteurActiviteOffre() {
        return barSecteurActiviteOffre;
    }

    public void setBarSecteurActiviteOffre(BarChartModel barSecteurActiviteOffre) {
        this.barSecteurActiviteOffre = barSecteurActiviteOffre;
    }

    public PieChartModel getPieGenreMembreInscris() {
        return pieGenreMembreInscris;
    }

    public void setPieGenreMembreInscris(PieChartModel pieGenreMembreInscris) {
        this.pieGenreMembreInscris = pieGenreMembreInscris;
    }

    public PieChartModel getPieGenreMembreEnRechercheEmploie() {
        return pieGenreMembreEnRechercheEmploie;
    }

    public void setPieGenreMembreEnRechercheEmploie(PieChartModel pieGenreMembreEnRechercheEmploie) {
        this.pieGenreMembreEnRechercheEmploie = pieGenreMembreEnRechercheEmploie;
    }

    public BarChartModel getBarSecteurActiviteRecherche() {
        return barSecteurActiviteRecherche;
    }

    public void setBarSecteurActiviteRecherche(BarChartModel barSecteurActiviteRecherche) {
        this.barSecteurActiviteRecherche = barSecteurActiviteRecherche;
    }



    


    
    
    
    
    
    
}
