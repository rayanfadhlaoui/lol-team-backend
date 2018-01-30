

/* Map entity LolTeamUserEntity */
CREATE SEQUENCE user_id_seq START 1;

CREATE TABLE public.lt_user
(
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username text COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT user_name_unique UNIQUE (user_name)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.lt_user
    OWNER to postgres;


/*Map entity SummonerEntity */
    
CREATE SEQUENCE public.summoner_id_seq START 1;

CREATE TABLE public.summoner
(
    id integer NOT NULL DEFAULT nextval('summoner_id_seq'::regclass),
    account_id bigint NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    last_update date,
    CONSTRAINT summoner_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.summoner
    OWNER to postgres;