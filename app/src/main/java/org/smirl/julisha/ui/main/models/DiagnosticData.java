package org.smirl.julisha.ui.main.models;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import org.smirl.julisha.R;

public class DiagnosticData {


    public static String[] questions={
                "Votre niveau de fièvre est-il superieur à 38 °C ?\n\n" +
                        "La température normale du corps oscille entre 36 °C et 37,2 °C selon les personnes, le cycle féminin (elle monte avec l'ovulation) et le moment de la journée (elle grimpe le soir). On parle de fièvre à partir de 38 °C.",
                "Est-ce que vous avez une toux récente ? \n \n " +
                        "Toux récente signifie une toux que vous n'aviez pas avant ou si vous toussez de manière chronique, que votre toux s'est empirée.",
                "Avez-vous des difficultés à respirer ?",
                "Avez-vous une fatigue inhabituelle ces derniers jours ?",
                "Avez-vous mal à la gorge ?",
                "Avez-vous une impossibilité de manger ou boire depuis 24 heures ou plus ?",
                "Avez-vous des courbatures en dehors des douleurs musculaires liées à une activité sportive intense ?",
                "Avez-vous perdu l’odorat de manière brutale sans rapport avec le nez bouché ?",
                "Avez-vous la diarrhée ? \n \n Avoir la diarrhée signifie émettre au moins 3 selles molles ou liquides par jour ou à une fréquence (c’est à dire un nombre de fois pour une période de temps) anormale pour la personne."

        };
    public static String[] reponses = {"Oui", "Oui",  "Oui", "Oui","Oui","Oui","Oui","Oui","Oui","int"};

  public static   String[] choix = {
            "Oui", "Non",
            "Oui", "Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",
            "Oui","Non",

    };


    }



