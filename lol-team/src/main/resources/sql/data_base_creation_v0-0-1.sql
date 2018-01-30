

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

CREATE SEQUENCE public.champion_id_seq START 1;

CREATE TABLE public.champion
(
    id integer NOT NULL DEFAULT nextval('champion_id_seq'::regclass),
    champion_id bigint NOT NULL,
    champion_name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT champion_pkey PRIMARY KEY (id)
)

CREATE SEQUENCE public.match_id_seq START 1;

CREATE TABLE public.match
(
    id integer NOT NULL DEFAULT nextval('match_id_seq'::regclass),
    game_id bigint NOT NULL,
    game_duration bigint NOT NULL,
    game_mode text COLLATE pg_catalog."default" NOT NULL,
    game_version text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT match_pkey PRIMARY KEY (id)
)

CREATE SEQUENCE public.participant_stats_id_seq START 1;

CREATE TABLE public.participant_stats
(
    id integer NOT NULL DEFAULT nextval('participant_stats_id_seq'::regclass),
    summoner_id bigint NOT NULL,
    simple_stats_id bigint NOT NULL,
    match_id bigint NOT NULL,
    CONSTRAINT participant_stats_pkey PRIMARY KEY (id),
    CONSTRAINT match_id_fk FOREIGN KEY (match_id)
        REFERENCES public.match (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT simple_stats_id_fk FOREIGN KEY (simple_stats_id)
        REFERENCES public.simple_stats (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT summoner_id_fk FOREIGN KEY (summoner_id)
        REFERENCES public.summoner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE SEQUENCE public.simple_stats_id_seq START 1;

CREATE TABLE public.simple_stats
(
    id integer NOT NULL DEFAULT nextval('simple_stats_id_seq'::regclass),
    champion_id bigint NOT NULL,
    role text COLLATE pg_catalog."default",
    lane text COLLATE pg_catalog."default",
    farm_at_10 double precision,
    farm_at_10_to_20 double precision,
    farm_at_20_to_30 double precision,
    CONSTRAINT simple_stats_pkey PRIMARY KEY (id),
    CONSTRAINT champion_id_fk FOREIGN KEY (champion_id)
        REFERENCES public.champion (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE SEQUENCE public.team_id_seq START 1;

CREATE TABLE public.team
(
    id integer NOT NULL DEFAULT nextval('team_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT team_pkey PRIMARY KEY (id),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.lt_user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE SEQUENCE public.team_summoner_id_seq START 1;

CREATE TABLE public.team_summoner
(
    id integer NOT NULL DEFAULT nextval('team_summoner_id_seq'::regclass),
    summoner_id bigint NOT NULL,
    team_id bigint NOT NULL,
    CONSTRAINT team_summoner_pkey PRIMARY KEY (id),
    CONSTRAINT summoner_id_fk FOREIGN KEY (summoner_id)
        REFERENCES public.summoner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT team_id_fk FOREIGN KEY (team_id)
        REFERENCES public.team (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)