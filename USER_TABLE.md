
#### ⚠️ Why not to use reserved sql word `"user"` as a Table Name:

| Layer / Tool                | What Goes Wrong                                                                 | Fix / Workaround                                                                                  |
|----------------------------|----------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| **PostgreSQL**             | `"user"` is a reserved keyword — unquoted `user` is invalid                      | Always use `"user"` (quoted and lowercase)                                                        |
| **Liquibase `<loadData>`**| Works **only** if `tableName="user"` and `liquibase.quotedIdentifiers=true`      | Set `liquibase.quotedIdentifiers=true`, and do not quote in `tableName` attribute                 |
| **Liquibase `<sql>`**      | Identifiers like `"USER"` get uppercased unless quoted manually                  | Always manually write `"user"` inside raw `<sql>` blocks                                          |
| **JPA / Hibernate**        | Default table name resolves to `user` → leads to syntax error in PostgreSQL      | Use `@Table(name = "\"user\"")` to explicitly quote and preserve lowercase                        |
| **Native SQL (`@Query`)**  | Same PostgreSQL rules apply — `user` is invalid unless quoted                    | Use `"user"` in JPQL or native SQL if referring to table directly                                 |
| **H2 (in-memory DB)**      | Case-insensitive by default → `"user"` and `USER` behave differently             | Use `MODE=PostgreSQL` and quoted identifiers to mimic real behavior                              |
| **pgAdmin / DBeaver**      | UI tools may auto-uppercase identifiers                                          | Always verify table casing; use `"user"` consistently in queries                                  |
| **SQL export/import**      | Tools may drop quotes or change case in scripts                                  | Use flags like `--quote-all-identifiers` when exporting                                           |
| **Code generation tools**  | May generate invalid SQL/Java for reserved names without proper config           | Customize templates or alias `"user"` as `usr` or similar                                         |
| **Testing environments**   | Unquoted or misquoted names can silently pass in test DBs (e.g., H2)             | Ensure test DB mimics production settings (e.g., use Dockerized PostgreSQL)                       |

