/*********************************************************
*
*-- fValidate US-English language file.
*
*   Translation by: Peter Bailey
*   Email: me@peterbailey.net
*
*   Visit http://www.peterbailey.net/fValidate/api/i18n/
*   for additional translations and instructions on
*   making your own translation.
*
*   !!! WARNING !!! Changing anything but the literal 
*   strings will likely cause script failure!
*
*   Note: This document most easily read/edited with tab-
*   spacing set to 4
*
*********************************************************/

if ( typeof fvalidate == 'undefined' )
{
    var fvalidate = new Object();
}

fvalidate.i18n =
{
    //  Validation errors
    errors:
    {
        blank:      [
            ["S'il vous pla\u00EEt entrez la valeur pour '", 0,"' champ."]
            ],
        length:     [
            [0, " doit \u00eatre au moins ", 1, " caract\u00e8res"],
            [0, " doit pas \u00eatre sup\u00e9rieur \u00e0 ", 1, " caract\u00e8res.\nLe texte actuel est ", 2, " caract\u00e8res."]
            ],
        equalto:    [
            [0, " must be equal to ", 1]
            ],
        number:     [
            ["Le num\u00e9ro que vous avez entr\u00e9 pour '", 0,"' champ non valide. Specify the value between ", 1, " and ", 2],
            ["Le num\u00E9ro que vous avez entr\u00E9 pour '", 0,"' champ non valide"]
            ],
        numeric:    [
            ["Seules les valeurs num\u00E9riques sont valables pour la ", 0],
            ["Un minimum de ", 0, " les valeurs num\u00E9riques sont requis pour la ", 1]
            ],
        alnum:      [
            ["Les donn\u00E9es que vous avez entr\u00E9, \"", 0, "\", ne correspond pas au format requis pour ", 1,  
            "\nLongueur minimum: ", 2,
            "\nCase: ", 3,
            "\nChiffres autoris\u00E9s: ", 4,
            "\nEspaces autoris\u00E9s: ", 5,
            "\nCaract\u00e8res de ponctuation admis: ", 6, "\n"]
            ],
        decimal:    [
            [0, " is not valid. Please re-enter a valid decimal number for ", 1," with number of digits before decimal point is ",2," and number of digits after the decimal point is ",3]
            ],
        decimalr:   [
        [0, " n'est pas valide. S'il vous pla\u00eet entrer de nouveau une valeur conforme aux r\u00e8gles suivantes: \ n-Le nombre de chiffres sur le c\u00f4t\u00e9 gauche du s\u00e9parateur d\u00e9cimal doit \u00eatre comprise entre ",1," et ",2,".\n-Le nombre de chiffres sur le c\u00f4t\u00e9 droit du s\u00e9parateur d\u00e9cimal doit \u00eatre comprise entre ",3," et ",4,"."]
            ],
        ip:         [
            ["S'il vous pla\u00EEt entrer une adresse IP valide pour '",0,"' champ."],
            ["Le num\u00E9ro de port que vous avez sp\u00E9cifi\u00E9, ", 0, ",  est hors de port\u00E9e. \ nIl doit \u00EAtre comprise entre ", 1, " et ", 2]
            ],
        ssn:        [
            ["Vous devez entrer un num\u00E9ro de s\u00E9curit\u00E9 sociale valide pour '",0,"' champ.\nVotre SSN doit \u00EAtre inscrit dans 'XXX-XX-XXXX' format."]
            ],
        money:      [
            [0, " ne correspond pas au format requis ", 1]
            ],
        cc:         [
            ["Le ", 0, "que vous avez entr\u00E9 n'est pas valide. S'il vous pla\u00EEt v\u00E9rifier de nouveau et r\u00E9-entrer."]
            ],
        ccDate:     [
            ["Votre carte de cr\u00E9dit a expir\u00E9! S'il vous pla\u00EEt utiliser une autre carte."]
            ],
        zip:        [
            ["S'il vous pla\u00EEt, entrer un code \u00E0 5 ou 9 chiffres postal valide pour '",0,"' champ."]
            ],
        phone:      [
            ["Please enter a valid phone number plus Area Code for '", 0, "' field. Phone number must be entered in '(XXX) XXX-XXXX' format for strict validation."],               
            ["S'il vous pla\u00EEt, entrez un num\u00E9ro de t\u00E9l\u00E9phone valide - sept ou dix chiffres."]
            ],
        email:      [
            ["S'il vous pla\u00EEt entrer une adresse \u00E9lectronique valide pour '",0,"' champ."]
            ],
        url:        [
            [0, " Le domaine n'est pas valide"]
            ],
        date:       [
            ["Les donn\u00E9es saisies pour ", 0, " n'est pas une date valide\nS'il vous pla\u00EEt entrer une date en utilisant le format suivant: ", 1],
            ["La date doit \u00EAtre avant le ", 0],
            ["La date doit \u00EAtre le ou avant le ", 0],
            ["La date doit \u00eatre apr\u00e8s le ", 0],
            ["La date doit \u00eatre le ou apr\u00e8s le ", 0]
            ],
        select:     [
            ["S'il vous pla\u00EEt s\u00E9lectionnez une option valide pour ", 0]
            ],
        selectm:    [
            ["S'il vous pla\u00EEt s\u00E9lectionnez une option pour ", 0 ]
            ],
        selecti:    [
            ["S'il vous pla\u00EEt s\u00E9lectionnez une option valide pour ", 0]
            ],
        checkbox:   [
            ["S'il vous pla\u00EEt v\u00E9rifier ", 2, " avant de poursuivre"],
            ["S'il vous pla\u00EEt choisir entre ", 0, " et ", 1, " options pour ", 2, ".\nVous avez actuellement ", 3, " s\u00E9lectionn\u00E9s"],
            ["Please check ", 0, " before continuing"]
            ],
        radio:      [
            ["S'il vous pla\u00EEt v\u00E9rifier ", 0, " avant de poursuivre"],
            ["S'il vous pla\u00EEt s\u00E9lectionnez une option pour ", 0 ]
            ],
        comparison: [
            [0, " doit \u00EAtre ", 1, " ", 2]
            ],
        eitheror:   [
            ["Un et un seul des champs suivants doivent \u00EAtre entr\u00E9s:\n\t-", 0, "\n"]
            ],
        atleast:    [
            ["Au moins ", 0, " des champs suivants doivent \u00EAtre entr\u00E9s:\n\t-", 1, "\n\nVous n'avez que ", 2, " rempli.\n"]
            ],
        allornone:  [
            ["Tous ou aucun des domaines suivants doivent \u00EAtre consign\u00E9s et pr\u00E9cis:\n\t-", 0, "\nVous n'avez que ", 1, " pr\u00E9cise sur le champ entr\u00E9e.\n"]
            ],
        file:       [
            ["Le fichier doit \u00EAtre l'un des types suivants:\n", 0, "\nNote: Extension du fichier mai \u00EAtre sensibles \u00E0 la diff\u00E9rence entre majuscules et minuscules."]
            ],
        custom:     [
            [0, " est invalide."]
            ],
        cazip:      [
            ["S'il vous pla\u00EEt entrer un code postal valide."]
            ],
        ukpost:     [
            ["S'il vous pla\u00EEt entrer un code postal valide."]
            ],
        germanpost: [
            ["S'il vous pla\u00EEt entrer un code postal valide."]
            ],
        swisspost:  [
            ["S'il vous pla\u00EEt entrer un code postal valide."]
            ],
        document:   [
            ["S'il vous pla\u00EEt joindre un document pour '",0,"' champ."]
            ],
        xml:    [
            ["S'il vous pla\u00EEt joindre un fichier XML pour '",0,"' champ."]
            ],
        datetime:   [
            ["Please enter a valid date in '",1,"' format for ",0,"."]
            ],
        bizsite_instanceName:[
      ["Please enter a valid Instance Name which does not contain any special characters like "]
            ],
        datafield_decimal:[
            [0]
        ]
    },

    comparison:
    {
        gt:     "sup\u00E9rieur",
        lt:     "moins de",
        gte:    "sup\u00E9rieure ou \u00E9gale \u00E0",
        lte:    "inf\u00E9rieur ou \u00E9gal \u00E0",
        eq:     "\u00E9gal \u00E0",
        neq:    "pas \u00E9gal \u00E0"
    },

    //  Developer assist errors
    devErrors:
    {
        number:     ["La limite inf\u00E9rieure(", 0, ") est sup\u00E9rieure \u00E0 la limite sup\u00E9rieure (", 1, ") sur cet \u00E9l\u00E9ment: ", 2],
        length:     ["La longueur minimale (", 0, ") est sup\u00E9rieure \u00E0 la longueur maximale (", 1, ") sur cet \u00E9l\u00E9ment: ", 2],
        cc:         ["Type de carte de cr\u00E9dit (", 0, ") not found."],

        lines:      ["! ATTENTION! -- fValidate aider les d\u00E9veloppeurs d'erreur\n", "\nSi vous n'\u00EAtes pas le promoteur, s'il vous pla\u00EEt contactez l'administrateur du site Internet concernant cette erreur."],
        paramError: ["Vous devez inclure les '", 0, "' param\u00e8tre pour la '", 1, "' validateur sur ce type de champ: ", 2],
        notFound:   ["Le validateur'", 0, "' n'a pas \u00E9t\u00E9 trouv\u00E9.\nDemand\u00E9 par: ", 1],
        noLabel:    ["Aucun \u00E9l\u00E9ment trouv\u00E9 pour l'\u00E9tiquette: ", 0],
        noBox:      ["Un \u00E9l\u00E9ment dont l'id demand\u00E9'", 0, "' n'a pas \u00E9t\u00E9 trouv\u00E9 pour la 'boxError' config valeur."],
        missingName:["L'entr\u00E9e cach\u00E9e appelant le validateur logique suivantes doivent avoir un nom valide\nattribut lorsqu'il est utilis\u00E9 conjointement avec le 'box' Erreur-type.\n\t", 0],
        mismatch:   ["Validateur / \u00c9l\u00e9ment de type discordance.\n\n\u00c9l\u00e9ment: ", 0, "\n\u00c9l\u00e9ment de type: ", 1, "\nType requis par validateur: ", 2],
        noCCType:   ["Vous devez inclure un \u00E9l\u00E9ment SELECT avec des choix de type de carte de cr\u00E9dit!"]
    },

    //  Config values
    config :
    {
        confirmMsg :        "Vos donn\u00E9es sont sur le point d'\u00EAtre envoy\u00E9.\nS'il vous pla\u00EEt cliquez sur 'Ok' de proc\u00E9der ou 'Cancel' d'avorter.",
        confirmAbortMsg :   "Soumission annul\u00E9e. Donn\u00E9es n'ont pas \u00E9t\u00E9 communiqu\u00E9es."
    },

    //  Tooltip attached to Box-item errors
    boxToolTip: "Cliquez pour objectif de champ",

    //  Message displayed at top of alert error in group mode
    groupAlert: "Les erreurs suivantes se sont produites:\n\n- ",

    //  Literal translation of the English 'or', include padding spaces.
    or:         " or ",
    creditCardType:    
    {
        VISA:  "VISA",
        MC:  "Master Card",
        DISC:  "Discover",
        AMEX:  "American Express"
    }
}