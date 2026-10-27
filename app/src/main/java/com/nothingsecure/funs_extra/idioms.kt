package com.nothingsecure.funs_extra

import android.content.SharedPreferences


class idioms_instance (val pref: SharedPreferences, val block: Int) {
    data class data_idioms (val en: String, val es: String = "", val ru: String = "", val ch: String = "", val jp: String = "")

    val idioms_list = mapOf(
        0 to mapOf( // extra
            0 to data_idioms("Ok", "Aceptar", "ОК", "確定", "OK"),
            1 to data_idioms("salt", "sal", "соль", "鹽", "ソルト"),
            2 to data_idioms("password", "contraseña", "пароль", "密碼", "パスワード"),
            3 to data_idioms("No file", "Sin archivo", "Нет файла", "沒有文件", "ファイルがありません"),
            4 to data_idioms("Empty file", "Archivo vacío", "Пустой файл", "空文件", "空のファイル"),
            5 to data_idioms("No password has been specified", "No se ha especificado ninguna contraseña", "Пароль не указан", "未指定密碼", "パスワードが指定されていません"),
            6 to data_idioms("The password is not valid", "La contraseña no es válida", "Неверный пароль", "密碼無效", "パスワードが無効です"),
            7 to data_idioms("Decryption error", "Error de descifrado", "Ошибка расшифровки", "解密錯誤", "復号エラー"),
            8 to data_idioms("Encrypting the data", "Cifrando los datos", "Шифрование данных", "正在加密數據", "データを暗号化中"),
            9 to data_idioms("Adding to the database", "Añadiendo a la base de datos", "Добавление в базу данных", "正在添加到數據庫", "データベースに追加中"),
            10 to data_idioms("Encryption error", "Error de cifrado", "Ошибка шифрования", "加密錯誤", "暗号化エラー"),
            11 to data_idioms("decrypting the data", "descifrando los datos", "расшифровка данных", "正在解密數據", "データを復号中"),
            12 to data_idioms("Extracting data from your database", "Extrayendo datos de tu base de datos", "Извлечение данных из вашей базы данных", "正在從您的數據庫中提取數據", "データベースからデータを抽出中"),
            13 to data_idioms("authentication error", "error de autenticación", "ошибка аутентификации", "身份驗證錯誤", "認証エラー"),
            14 to data_idioms("Better not", "Mejor no", "Лучше не надо", "最好不要", "やめておく"),
            15 to data_idioms("Extracting your key", "Extrayendo tu clave", "Извлечение вашего ключа", "正在提取您的金鑰", "キーを抽出中"),
            16 to data_idioms("Generating the emails", "Generando los correos electrónicos", "Генерация электронных писем", "正在生成電子郵件", "メールを生成中"),
            17 to data_idioms("I'd love to", "Me encantaría", "С удовольствием", "我很樂意", "喜んで"),
            18 to data_idioms("Don't close the app", "No cierres la aplicación", "Не закрывайте приложение", "請勿關閉應用程序", "アプリを閉じないでください"),
            19 to data_idioms("pause", "pausa", "пауза", "暫停", "一時停止"),
            20 to data_idioms("G1 verification", "Verificación G1", "Верификация G1", "G1 验证", "G1検証"),
        ),
        1 to mapOf( // regster activity
            0 to data_idioms("Why can't I log in?", "¿Por qué no puedo iniciar sesión?", "Почему я не могу войти?", "為什麼我無法登錄？", "なぜログインできないのですか？"),
            1 to data_idioms(
                "To use NowiVault and for your security, you need to create a biometric profile.\nTo do this, go to settings and use your password to set up a biometric data profile",
                "Para usar NowiVault y por tu seguridad, necesitas crear un perfil biométrico.\nPara hacerlo, ve a la configuración y usa tu contraseña para configurar un perfil de datos biométricos",
                "Для использования NowiVault и в целях безопасности необходимо создать биометрический профиль.\nДля этого перейдите в настройки и используйте пароль для настройки биометрического профиля",
                "為了使用 NowiVault 並出於您的安全考慮，您需要創建生物識別個人資料。\n為此，請前往設置並使用您的密碼設置生物識別數據個人資料",
                "NowiVaultを使用するため、またセキュリティのために、生体認証プロファイルを作成する必要があります。\nこれを行うには、設定に移動し、パスワードを使用して生体認証データプロファイルを設定してください"
            ),
            2 to data_idioms("Go to settings", "Ir a la configuración", "Перейти в настройки", "前往設置", "設定へ移動"),
            3 to data_idioms("Put your password", "Introduce tu contraseña", "Введите ваш пароль", "輸入您的密碼", "パスワードを入力"),
            4 to data_idioms("Create your password", "Crea tu contraseña", "Создайте ваш пароль", "創建您的密碼", "パスワードを作成"),
            5 to data_idioms("NowiVault closed due to a forced action", "NowiVault se cerró debido a una acción forzada", "NowiVault закрыт из-за принудительного действия", "NowiVault 因強制操作而關閉", "強制的なアクションによりNowiVaultが終了しました"),
            6 to data_idioms("Make sure no one is looking at your screen", "Asegúrate de que nadie esté mirando tu pantalla", "Убедитесь, что никто не смотрит на ваш экран", "確保沒有人在看您的螢幕", "誰も画面を見ていないことを確認してください"),
            7 to data_idioms("Successful login", "Inicio de sesión exitoso", "Успешный вход", "登錄成功", "ログイン成功"),
            8 to data_idioms("Login failed", "Error al iniciar sesión", "Ошибка входа", "登錄失敗", "ログイン失敗"),
            9 to data_idioms("Authenticating your key", "Autenticando tu clave", "Аутентификация вашего ключа", "正在驗證您的金鑰", "キーを認証中"),
            10 to data_idioms("Creating the cryptographic key", "Creando la clave criptográfica", "Создание криптографического ключа", "正在創建加密金鑰", "暗号化キーを作成中"),
            11 to data_idioms("Creating the log key", "Creando la clave de registro", "Создание ключа журнала", "正在創建日誌金鑰", "ログキーを作成中"),
            12 to data_idioms("8 or more characters, please", "8 o más caracteres, por favor", "8 или более символов, пожалуйста", "請輸入 8 個或更多字元", "8文字以上を入力してください"),
            13 to data_idioms("Information missing", "Falta información", "Информация отсутствует", "缺少資訊", "情報が不足しています"),
            14 to data_idioms("The hash you specified is invalid", "El hash especificado no es válido", "Указанный хэш недействителен", "您指定的哈希值無效", "指定されたハッシュが無効です"),
            15 to data_idioms("APK Verifier?", "¿Verificador de APK?", "Проверка APK?", "APK 驗證器？", "APK検証ツール？"),
            16 to data_idioms(
                "This APK checker lets you know if the hash you specify matches that of the APK you upload directly from NowiVault. The hash must be calculated using the \"SHA256\" algorithm, and only the hexadecimal representation should be provided.",
                "Este comprobador de APK te permite saber si el hash que especificas coincide con el del APK que subes directamente desde NowiVault. El hash debe calcularse mediante el algoritmo \"SHA256\" y solo se debe proporcionar la representación hexadecimal.",
                "Эта проверка APK позволяет узнать, совпадает ли указанный вами хэш с хэшем APK, загруженного непосредственно из NowiVault. Хэш должен быть рассчитан с использованием алгоритма \"SHA256\", и должно быть предоставлено только шестнадцатеричное представление.",
                "此 APK 檢查器可讓您了解您指定的哈希值是否與您直接從 NowiVault 上傳的 APK 哈希值相符。哈希值必須使用 \"SHA256\" 算法計算，並且只能提供十六進制表示形式。",
                "このAPKチェッカーにより、指定したハッシュがNowiVaultから直接アップロードしたAPKのハッシュと一致するかどうかを確認できます。ハッシュは「SHA256」アルゴリズムを使用して計算し、16進数表記のみを指定する必要があります。"
            ),
            17 to data_idioms("Key derivation?", "¿Derivación de clave?", "Дерривация ключа?", "金鑰衍生？", "鍵導出？"),
            18 to data_idioms(
                "NowiVault's key derivation mode does away with the KeyStore for storing cryptographic keys and uses PBKDF2 (an algorithm for deriving cryptographic keys) to generate your cryptographic key when you need it.\nThis architecture does not store your cryptographic key, providing security similar to storing it in a TEE or a StrongBox",
                "El modo de derivación de claves de NowiVault prescinde del KeyStore para almacenar claves criptográficas y utiliza PBKDF2 (un algoritmo para derivar claves criptográficas) para generar tu clave criptográfica cuando la necesitas.\nEsta arquitectura no almacena tu clave criptográfica, proporcionando una seguridad similar a guardarla en un TEE o un StrongBox",
                "Режим деривации ключей NowiVault обходится без KeyStore для хранения криптографических ключей и использует PBKDF2 (алгоритм для деривации криптографических ключей) для генерации вашего криптографического ключа, когда это необходимо.\nЭта архитектура не хранит ваш криптографический ключ, обеспечивая безопасность, аналогичную хранению в TEE или StrongBox",
                "NowiVault 的金鑰衍生模式摒棄了用於存儲加密金鑰的 KeyStore，並使用 PBKDF2（一種用於衍生加密金鑰的算法）在您需要時生成加密金鑰。\n這種架構不存儲您的加密金鑰，提供類似於將其存儲在 TEE 或 StrongBox 中的安全性",
                "NowiVaultの鍵導出モードは、暗号化キーを保存するためのKeyStoreを使用せず、必要なときにPBKDF2（暗号化キーを導出するためのアルゴリズム）を使用して暗号化キーを生成します。\nこのアーキテクチャは暗号化キーを保存しないため、TEEやStrongBoxに保存するのと同様のセキュリティを提供します"
            ),
            19 to data_idioms("Calculating the Hash", "Calculando el Hash", "Расчет хэша", "正在計算哈希值", "ハッシュを計算中"),
            20 to data_idioms("The hashes have matched", "Los hashes coinciden", "Хэши совпали", "哈希值匹配", "ハッシュが一致しました"),
            21 to data_idioms("The hash did not match", "El hash no coincide", "Хэш не совпал", "哈希值不匹配", "ハッシュが一致しませんでした"),
            22 to data_idioms("iterations", "iteraciones", "итераций", "迭代次數", "反復回数"),
            23 to data_idioms("Derived Key mode", "Modo de Clave Derivada", "Режим производного ключа", "衍生金鑰模式", "派生キーモード"),
            24 to data_idioms("Verifies the hash of an APK", "Verifica el hash de un APK", "Проверяет хэш APK", "驗證 APK 的哈希值", "APKのハッシュを検証します")
        ),
        2 to mapOf( // main activity
            0 to data_idioms("You don't have passwords on your file", "No tienes contraseñas en tu archivo", "У вас нет паролей в файле", "您的檔案中沒有密碼", "ファイルにパスワードがありません"),
            1 to data_idioms("Obtaining the key", "Obteniendo la clave", "Получение ключа", "正在獲取金鑰", "キーを取得中"),
            2 to data_idioms("Do you want to delete this data?", "¿Quieres eliminar estos datos?", "Вы хотите удалить эти данные?", "您要刪除此數據嗎？", "このデータを削除しますか？"),
            3 to data_idioms("If you delete this record from your database, you will not be able to recover it", "Si eliminas este registro de tu base de datos, no podrás recuperarlo", "Если вы удалите эту запись из базы данных, вы не сможете ее восстановить", "如果您從數據庫中刪除此記錄，您將無法復原它", "データベースからこのレコードを削除すると、復元できなくなります"),
            4 to data_idioms("Delete", "Eliminar", "Удалить", "刪除", "削除"),
            5 to data_idioms("better not", "mejor no", "лучше не надо", "最好不要", "やめておく"),
            6 to data_idioms("Data removed", "Datos eliminados", "Данные удалены", "數據已刪除", "データが削除されました"),
            7 to data_idioms("A password has been deleted", "Se ha eliminado una contraseña", "Пароль был удален", "密碼已被刪除", "パスワードが削除されました"),
            8 to data_idioms("Log", "Registro", "Журнал", "日誌", "ログ"),
            9 to data_idioms("deleting the log", "eliminando el registro", "удаление журнала", "正在刪除日誌", "ログを削除中"),
            10 to data_idioms("A log has been deleted", "Se ha eliminado un registro", "Запись журнала удалена", "一條日誌已被刪除", "ログが削除されました"),
            11 to data_idioms("Do you want to delete all the logs?", "¿Quieres eliminar todos los registros?", "Вы хотите удалить все журналы?", "您要刪除所有日誌嗎？", "すべてのログを削除しますか？"),
            12 to data_idioms("If you delete all the logs, you will lose full access to them", "Si eliminas todos los registros, perderás el acceso completo a ellos", "Если вы удалите все журналы, вы потеряете к ним полный доступ", "如果您刪除所有日誌，您將失去對它們的完全存取權", "すべてのログを削除すると、それらへのアクセス権が完全に失われます"),
            13 to data_idioms("All logs have been deleted", "Se han eliminado todos los registros", "Все журналы удалены", "所有日誌已被刪除", "すべてのログが削除されました"),
            14 to data_idioms("There is nothing to export", "No hay nada que exportar", "Нечего экспортировать", "沒有可導出的內容", "エクスポートするものがありません"),
            15 to data_idioms("What am I exporting?", "¿Qué estoy exportando?", "Что я экспортирую?", "我正在導出什麼？", "何をエクスポートしていますか？"),
            16 to data_idioms(
                "When you export, you are exporting all your passwords to a \".ns\" file with the cryptographic settings you specified.\nYou can import them back into NowiVault whenever you want.",
                "Al exportar, estás exportando todas tus contraseñas a un archivo \".ns\" con las configuraciones criptográficas que especificaste.\nPuedes volver a importarlas a NowiVault cuando quieras.",
                "При экспорте вы экспортируете все свои пароли в файл \".ns\" с указанными вами криптографическими настройками.\nВы можете импортировать их обратно в NowiVault в любое время.",
                "導出時，您會將所有密碼導出到具有您指定的加密設置的 \".ns\" 檔案中。\n您可以隨時將它們重新導入 NowiVault。",
                "エクスポートすると、指定した暗号化設定ですべてのパスワードが「.ns」ファイルにエクスポートされます。\nいつでもNowiVaultに再インポートできます。"
            ),
            17 to data_idioms("Do you want to delete your database after exporting?", "¿Quieres eliminar tu base de datos después de exportar?", "Вы хотите удалить свою базу данных после экспорта?", "導出後要刪除數據庫嗎？", "エクスポート後にデータベースを削除しますか？"),
            18 to data_idioms(
                "If you delete your database after exporting, you will no longer be able to access it in any way within NowiVault. You will only be able to access it using your exported file.",
                "Si eliminas tu base de datos después de exportar, ya no podrás acceder a ella de ninguna manera dentro de NowiVault. Solo podrás acceder a ella utilizando tu archivo exportado.",
                "Если вы удалите свою базу данных после экспорта, вы больше не сможете получить к ней доступ через NowiVault. Вы сможете получить к ней доступ только с помощью экспортированного файла.",
                "如果您在導出後刪除數據庫，您將無法再在 NowiVault 中以任何方式存取它。您只能使用導出的檔案進行存取。",
                "エクスポート後にデータベースを削除すると、NowiVault内でそれらにアクセスすることはできなくなります。エクスポートしたファイルを使用してのみアクセスできるようになります。"
            ),
            19 to data_idioms("Generating the file", "Generando el archivo", "Генерация файла", "正在生成檔案", "ファイルを生成中"),
            20 to data_idioms("HoneyPot mode has been activated", "El modo HoneyPot ha sido activado", "Режим HoneyPot активирован", "HoneyPot 模式已激活", "HoneyPotモードが有効化されました"),
            21 to data_idioms("Back to normal", "Volver a la normalidad", "Возврат к норме", "恢復正常", "通常に戻る"),
            22 to data_idioms("Bye", "Adiós", "Пока", "再見", "さようなら"),
            23 to data_idioms("start mode", "modo de inicio", "режим запуска", "啟動模式", "起動モード"),
            24 to data_idioms("What does \"preview\" mode do?", "¿Qué hace el modo \"vista previa\"?", "Что делает режим \"просмотра\"?", "\"預覽\"模式有何作用？", "「プレビュー」モードは何をしますか？"),
            25 to data_idioms("Preview mode lets you securely view passwords from a file without having to add them to your database.", "El modo de vista previa te permite ver de forma segura las contraseñas de un archivo sin tener que añadirlas a tu base de datos.", "Режим просмотра позволяет безопасно просматривать пароли из файла без необходимости добавлять их в базу данных.", "預覽模式可讓您安全地查看檔案中的密碼，而無需將其添加到數據庫中。", "プレビューモードでは、データベースに追加することなく、ファイルからパスワードを安全に表示できます。"),
            26 to data_idioms("What does \"add_db\" mode do?", "¿Qué hace el modo \"añadir_bd\"?", "Что делает режим \"add_db\"?", "\"add_db\" 模式有何作用？", "「add_db」モードは何をしますか？"),
            27 to data_idioms("This mode allows you to add information to your database without losing the rest of the data.", "Este modo te permite añadir información a tu base de datos sin perder el resto de los datos.", "Этот режим позволяет добавлять информацию в базу данных без потери остальных данных.", "此模式允許您向數據庫添加資訊而不會丟失其餘數據。", "このモードを使用すると、他のデータを失うことなくデータベースに情報を追加できます。"),
            28 to data_idioms("What does \"replace_db\" mode do?", "¿Qué hace el modo \"reemplazar_bd\"?", "Что делает режим \"replace_db\"?", "\"replace_db\" 模式有何作用？", "「replace_db」モードは何をしますか？"),
            29 to data_idioms("This mode allows you to replace your database with the file to be imported.", "Este modo te permite reemplazar tu base de datos con el archivo que vas a importar.", "Этот режим позволяет заменить вашу базу данных импортируемым файлом.", "此模式允許您使用要導入的檔案替換數據庫。", "このモードを使用すると、データベースをインポートするファイルと置き換えることができます。"),
            30 to data_idioms("import", "importar", "импорт", "導入", "インポート"),
            31 to data_idioms("Better another mode", "Mejor otro modo", "Лучше другой режим", "最好選擇另一種模式", "別のモードにする"),
            32 to data_idioms("The extension is incorrect", "La extensión es incorrecta", "Неверное расширение", "副檔名不正確", "拡張子が正しくありません"),
            33 to data_idioms("The file structure is not correct", "La estructura del archivo no es correcta", "Неверная структура файла", "檔案結構不正確", "ファイル構造が正しくありません"),
            34 to data_idioms(
                "You are using an older version of the file; it is recommended that you re-export your password file to update to the new version.",
                "Estás utilizando una versión antigua del archivo; se recomienda que vuelvas a exportar tu archivo de contraseñas para actualizarlo a la nueva versión.",
                "Вы используете старую версию файла; рекомендуется повторно экспортировать файл паролей для обновления до новой версии.",
                "您使用的是較舊版本的檔案；建議您重新導出密碼檔案以更新到新版本。",
                "古いバージョンのファイルを使用しています。新しいバージョンに更新するには、パスワードファイルを再エクスポートすることをお勧めします。"
            ),
            35 to data_idioms("Checking the legitimacy of the key", "Comprobando la legitimidad de la clave", "Проверка легитимности ключа", "正在檢查金鑰的正當性", "キーの正当性を確認中"),
            36 to data_idioms("Export your file", "Exporta tu archivo", "Экспортируйте ваш файл", "導出您的檔案", "ファイルをエクスポート"),
            37 to data_idioms("delete the database after exporting", "eliminar la base de datos después de exportar", "удалить базу данных после экспорта", "導出後刪除數據庫", "エクスポート後にデータベースを削除"),
            38 to data_idioms("Import the file", "Importar el archivo", "Импортировать файл", "導入檔案", "ファイルをインポート"),
            41 to data_idioms("What do you want to do", "¿Qué quieres hacer?", "Что вы хотите сделать", "您想做什麼", "何をしたいですか"),
            42 to data_idioms("What do you want to do with your file?", "¿Qué quieres hacer con tu archivo?", "Что вы хотите сделать с вашим файлом?", "您想對您的檔案做什麼？", "ファイルで何をしたいですか？")
        ),
        3 to mapOf( // configuration activity
            0 to data_idioms("Do you want to leave a comment about NowiVault?", "¿Quieres dejar un comentario sobre NowiVault?", "Вы хотите оставить отзыв о NowiVault?", "您想留下關於 NowiVault 的評論嗎？", "NowiVaultについてコメントを残しますか？"),
            1 to data_idioms("All feedback regarding improvements to NowiVault is actively addressed by \"cuadratico\" and compiled for continuous improvement", "Todos los comentarios sobre las mejoras de NowiVault son atendidos activamente por \"cuadratico\" y recopilados para una mejora continua", "Все отзывы об улучшениях NowiVault активно обрабатываются \"cuadratico\" и собираются для непрерывного улучшения", "所有關於 NowiVault 改進的反饋都由 \"cuadratico\" 積極處理並匯總以持續改進", "NowiVaultの改善に関するすべてのフィードバックは「cuadratico」によって積極的に取り組まれ、継続的な改善のためにまとめられます"),
            2 to data_idioms("Do you want to donate to the Nowi project?", "¿Quieres donar al proyecto Nowi?", "Вы хотите сделать пожертвование проекту Nowi?", "您想捐贈給 Nowi 項目嗎？", "Nowiプロジェクトに寄付しますか？"),
            3 to data_idioms(
                "At Nowi, we develop Android apps focused on local defensive security.\nBy donating, you are supporting the project's continuity.\n\nThanks for everything! \uD83D\uDE01",
                "En Nowi desarrollamos aplicaciones de Android enfocadas en la seguridad defensiva local.\nAl donar, estás apoyando la continuidad del proyecto.\n\n¡Gracias por todo! \uD83D\uDE01",
                "В Nowi мы разрабатываем приложения для Android, ориентированные на локальную оборонительную безопасность.\nДелая пожертвование, вы поддерживаете непрерывность проекта.\n\nСпасибо за все! \uD83D\uDE01",
                "在 Nowi，我們開發專注於本地防禦安全的 Android 應用程式。\n通過捐贈，您正在支持項目的持續發展。\n\n感謝一切！ \uD83D\uDE01",
                "Nowiでは、ローカルの防御的セキュリティに焦点を当てたAndroidアプリを開発しています。\n寄付することで、プロジェクトの継続を支援することになります。\n\nいつもありがとうございます！ \uD83D\uDE01"
            ),
            4 to data_idioms("Do you want to help kittens?", "¿Quieres ayudar a los gatitos?", "Вы хотите помочь котятам?", "您想幫助小貓嗎？", "子猫を助けたいですか？"),
            5 to data_idioms(
                "At Nowi, we believe kittens are essential to life, so why not help as many as possible? \uD83D\uDE3A",
                "En Nowi creemos que los gatitos son esenciales para la vida, así que ¿por qué no ayudar a tantos como sea posible? \uD83D\uDE3A",
                "В Nowi мы считаем, что котята необходимы для жизни, так почему бы не помочь как можно большему их количеству? \uD83D\uDE3A",
                "在 Nowi，我們相信小貓是生活中不可或缺的，所以為什麼不盡可能多地幫助它們呢？ \uD83D\uDE3A",
                "Nowiでは、子猫は生活に欠かせないものと考えています。できるだけ多くの子猫を助けませんか？ \uD83D\uDE3A"
            ),
            6 to data_idioms("settings button", "botón de configuración", "кнопка настроек", "設置按鈕", "設定ボタン"),
            7 to data_idioms("Here, you can configure the functionality of your \"settings button\" and its icon separately.", "Aquí puedes configurar la funcionalidad de tu \"botón de configuración\" y su icono por separado.", "Здесь вы можете отдельно настроить функциональность вашей \"кнопки настроек\" и ее значок.", "在這裡，您可以分別配置 \"設置按鈕\" 的功能及其圖標。", "ここでは、「設定ボタン」の機能とそのアイコンを個別に設定できます。"),
            8 to data_idioms("Menu color", "Color del menú", "Цвет меню", "菜單顏色", "メニューの色"),
            9 to data_idioms("Here you can configure the background color of the top menu.", "Aquí puedes configurar el color de fondo del menú superior.", "Здесь вы можете настроить цвет фона верхнего меню.", "波裏您可以配置頂部菜單的背景顏色。", "ここでは、トップメニューの背景色を設定できます。"),
            10 to data_idioms("login type", "tipo de inicio de sesión", "тип входа", "登錄類型", "ログインタイプ"),
            11 to data_idioms("Here, you can enable or disable the login button so that the login process is triggered based on the length of your password.", "Aquí puedes activar o desactivar el botón de inicio de sesión para que el proceso de inicio de sesión se active según la longitud de tu contraseña.", "Здесь вы можете включить или отключить кнопку входа, чтобы процесс входа запускался в зависимости от длины вашего пароля.", "波裏，您可以啟用或停用登錄按鈕，以便根據密碼長度觸發登錄流程。", "ここでは、ログインボタンを有効または無効にして、パスワードの長さに応じてログインプロセスが実行されるように設定できます。"),
            12 to data_idioms("Time-Out", "Tiempo de espera", "Тайм-аут", "超時", "タイムアウト"),
            13 to data_idioms("Here you can configure the NowiVault timeout, with the ability to extend or shorten your session duration.", "Aquí puedes configurar el tiempo de espera de NowiVault, con la posibilidad de ampliar o acortar la duración de tu sesión.", "Здесь вы можете настроить тайм-аут NowiVault с возможностью продления или сокращения продолжительности сеанса.", "波裏您可以配置 NowiVault 超時，並能夠延長或縮短您的會話持續時間。", "ここでは、セッション時間を延長または短縮する機能を備えたNowiVaultのタイムアウトを設定できます。"),
            14 to data_idioms("accelerometer detection", "detección por acelerómetro", "обнаружение акселерометром", "加速度計檢測", "加速度センサー検知"),
            15 to data_idioms("Here you can configure whether or not to enable accelerometer verification; this feature causes NowiVault to close if sudden movement is detected.", "Aquí puedes configurar si deseas activar la verificación por acelerómetro; esta función hace que NowiVault se cierre si se detecta un movimiento brusco.", "Здесь вы можете настроить, включать ли проверку акселерометра; эта функция приводит к закрытию NowiVault при обнаружении внезапного движения.", "波裏您可以配置是否啟用加速度計驗證；如果檢測到突然移動，此功能會導致 NowiVault 關閉。", "ここでは、加速度センサー検証を有効にするかどうかを設定できます。この機能により、急激な動きが検知された場合にNowiVaultが終了します。"),
            16 to data_idioms("Vibration", "Vibración", "Вибрация", "振動", "バイブレーション"),
            17 to data_idioms("Here you can configure whether to enable or disable vibration feedback in NowiVault.", "Aquí puedes configurar si deseas activar o desactivar la respuesta por vibración en NowiVault.", "Здесь вы можете настроить включение или отключение вибрационной обратной связи в NowiVault.", "波裏您可以配置是否在 NowiVault 中啟用或停用振動反饋。", "ここでは、NowiVaultでのバイブレーションフィードバックを有効にするか無効にするかを設定できます。"),
            18 to data_idioms("Animations", "Animaciones", "Анимации", "動畫", "アニメーション"),
            19 to data_idioms("This option enables or disables a 300-millisecond delay before dialogs appear; this allows you to see the exact contextual log of the running process, but it also adds a delay to your operations.", "Esta opción activa o desactiva un retraso de 300 milisegundos antes de que aparezcan los diálogos; esto te permite ver el registro contextual exacto del proceso en ejecución, pero también añade un retraso a tus operaciones.", "Эта опция включает или отключает 300-миллисекундную задержку перед появлением диалоговых окон; это позволяет увидеть точный контекстный журнал запущенного процесса, но также добавляет задержку в ваши операции.", "此選項可啟用或停用對話框出現前 300 毫秒的延遲；這允許您查看正在運行的進程的精確上下文日誌，但也會增加操作的延遲。", "このオプションは、ダイアログが表示される前の300ミリ秒の遅延を有効または無効にします。これにより、実行中のプロセスの正確なコンテキストログを確認できますが、操作に遅延が発生します。"),
            20 to data_idioms("What does this configuration do?", "¿Qué hace esta configuración?", "Что делает эта конфигурация?", "這個配置有何作用？", "この設定は何をしますか？")
        ),
        4 to mapOf( // recy_pass_funs
            0 to data_idioms("Edit your password", "Edita tu contraseña", "Редактировать пароль", "編輯您的密碼", "パスワードを編集"),
            1 to data_idioms("Add your password", "Añade tu contraseña", "Добавить пароль", "添加您的密碼", "パスワードを追加"),
            2 to data_idioms("Affair", "Asunto", "Дело", "事務/事項", "件名"),
            3 to data_idioms("You cannot copy passwords", "No puedes copiar contraseñas", "Вы не можете копировать пароли", "您無法複製密碼", "パスワードをコピーできません"),
            4 to data_idioms("You can copy", "Puedes copiar", "Вы можете копировать", "您可以複製", "コピーできます"),
            5 to data_idioms("A password has been copied", "Se ha copiado una contraseña", "Пароль скопирован", "密碼已被複製", "パスワードがコピーされました")
        ),
        5 to mapOf( // cip_funs
            0 to data_idioms("decrypted", "descifrado", "расшифровано", "已解密", "復号済み"),
            1 to data_idioms("A password has been added", "Se ha añadido una contraseña", "Пароль добавлен", "密碼已被添加", "パスワードが追加されました"),
            2 to data_idioms("A password has been edited", "Se ha editado una contraseña", "Пароль отредактирован", "密碼已被編輯", "パスワードが編集されました"),
            3 to data_idioms("Finishing processing the data", "Finalizando el procesamiento de datos", "Завершение обработки данных", "正在完成數據處理", "データの処理を完了中"),
            4 to data_idioms("The metadata have been decrypted", "Los metadatos han sido descifrados", "Метаданные расшифрованы", "元數據已被解密", "メタデータが復号されました")
        ),
        6 to mapOf( // key_conf
            0 to data_idioms("Your key length is", "La longitud de tu clave es", "Длина вашего ключа", "您的金鑰長度為", "キーの長さは"),
            1 to data_idioms("What exactly are interactions?", "¿Qué son exactamente las interacciones?", "Что такое итерации?", "究竟什麼是迭代/互動？", "反復処理とは正確には何ですか？"),
            2 to data_idioms(
                "When deriving a cryptographic key using the PBKDF2 algorithm, you need to specify a number of iterations—basically, the number of times your key will be hashed",
                "Al derivar una clave criptográfica utilizando el algoritmo PBKDF2, debes especificar un número de iteraciones; básicamente, el número de veces que se aplicará el hash a tu clave",
                "При деривации криптографического ключа с использованием алгоритма PBKDF2 вам необходимо указать количество итераций — по сути, количество раз, которое ваш ключ будет хэширован",
                "當使用 PBKDF2 算法衍生加密金鑰時，您需要指定迭代次數——基本上就是您的金鑰被哈希處理的次數",
                "PBKDF2アルゴリズムを使用して暗号化キーを導出する場合、反復回数（基本的にはキーがハッシュ化される回数）を指定する必要があります"
            ),
            3 to data_idioms("Do you want to use enhanced verification mode?", "¿Quieres utilizar el modo de verificación mejorado?", "Вы хотите использовать режим расширенной проверки?", "您想使用增強驗證模式嗎？", "拡張検証モードを使用しますか？"),
            4 to data_idioms(
                "Enhanced verification mode requires additional values to derive your key, reducing the number of encrypted values NowiVault stores and consequently increasing your security.\n\nIn this mode, you will be asked for your MasterKey, the iterations, and the salt of your derived key upon login.\n\nMemorize these or save them in KeePass.",
                "El modo de verificación mejorado requiere valores adicionales para derivar tu clave, lo que reduce la cantidad de valores cifrados que almacena NowiVault y, en consecuencia, aumenta tu seguridad.\n\nEn este modo, se te pedirá tu MasterKey, las iteraciones y la sal de tu clave derivada al iniciar sesión.\n\nMemorízalos o guárdalos en KeePass.",
                "Режим расширенной проверки требует дополнительных значений для деривации вашего ключа, что уменьшает количество зашифрованных значений, хранимых NowiVault, и, следовательно, повышает вашу безопасность.\n\nВ этом режиме при входе в систему вас попросят ввести MasterKey, итерации и соль вашего производного ключа.\n\nЗапомните их или сохраните в KeePass.",
                "增強驗證模式需要額外的值來衍生您的金鑰，從而減少 NowiVault 存儲的加密值的數量，進而提高您的安全性。\n\n在此模式下，登錄時將要求您輸入 MasterKey、迭代次數和衍生金鑰的鹽值。\n\n請記住這些或將它們保存在 KeePass 中。",
                "拡張検証モードでは、キーを導出するために追加の値が必要になるため、NowiVaultが保存する暗号化された値の数が減り、結果としてセキュリティが向上します。\n\nこのモードでは、ログイン時にMasterKey、反復回数、および派生キーのソルトが求められます。\n\nこれらを暗記するか、KeePassに保存してください。"
            ),
            5 to data_idioms("Increase my security", "Aumentar mi seguridad", "Повысить мою безопасность", "提高我的安全性", "セキュリティを強化する"),
            6 to data_idioms("Configure your cryptographic key", "Configura tu clave criptográfica", "Настройте ваш криптографический ключ", "配置您的加密金鑰", "暗号化キーを設定する")
        ),
        7 to mapOf( // e_i_file
            0 to data_idioms("Deriving the key from the file", "Derivando la clave del archivo", "Деривация ключа из файла", "正在從檔案中衍生金鑰", "ファイルからキーを導出中"),
            1 to data_idioms("Encrypting and building the file", "Cifrando y creando el archivo", "Шифрование и сборка файла", "正在加密並建立檔案", "ファイルを暗号化して構築中"),
            2 to data_idioms("Exporting the file", "Exportando el archivo", "Экспорт файла", "正在導出檔案", "ファイルをエクスポート中"),
            3 to data_idioms("Passwords have been exported", "Las contraseñas han sido exportadas", "Пароли экспортированы", "密碼已被導出", "パスワードがエクスポートされました"),
            4 to data_idioms("Exported file", "Archivo exportado", "Экспортированный файл", "已導出的檔案", "エクスポートされたファイル"),
            5 to data_idioms("Export error", "Error de exportación", "Ошибка экспорта", "導出錯誤", "エクスポートエラー"),
            6 to data_idioms("importing the file", "importando el archivo", "импорт файла", "正在導入檔案", "ファイルをインポート中"),
            7 to data_idioms("Crypto import operation error", "Error en la operación de importación criptográfica", "Ошибка операции криптоимпорта", "加密導入操作錯誤", "暗号化インポート操作エラー"),
            8 to data_idioms("importing the list", "importando la lista", "импорт списка", "正在導入列表", "リストをインポート中"),
            9 to data_idioms("A password file has been imported", "Se ha importado un archivo de contraseñas", "Файл паролей импортирован", "密碼檔案已被導入", "パスワードファイルがインポートされました"),
            10 to data_idioms("imported passwords", "contraseñas importadas", "импортированные пароли", "已導入的密碼", "インポートされたパスワード"),
            11 to data_idioms("import error", "error de importación", "ошибка импорта", "導入錯誤", "インポートエラー")
        ),
        8 to mapOf( // extra_fun
            0 to data_idioms("Suspicious movement has been detected", "Se ha detectado un movimiento sospechoso", "Обнаружено подозрительное движение", "檢測到可疑移動", "不審な動きが検知されました")
        ),
        9 to mapOf( // generators
            0 to data_idioms("Name or a text", "Nombre o un texto", "Имя или текст", "名稱或文本", "名前またはテキスト"),
            1 to data_idioms("Date or a text", "Fecha o un texto", "Дата или текст", "日期或文本", "日付またはテキスト"),
            2 to data_idioms("Something went wrong", "Algo salió mal", "Что-то пошло не так", "出錯了", "何かがうまくいきませんでした")
        ),
        10 to mapOf( // confi_funs
            0 to data_idioms(
                "The \"HoneyPot\" mode allows you to create a secure execution environment that mimics NowiVault without exposing your data. It is an excellent way to mitigate an attacker by making them believe they have successfully accessed your information. ¡¡To exit this mode, you will need to press the volume down button twice!!",
                "El modo \"HoneyPot\" te permite crear un entorno de ejecución seguro que imita a NowiVault sin exponer tus datos. Es una excelente manera de mitigar a un atacante haciéndole creer que ha accedido con éxito a tu información. ¡¡Para salir de este modo, tendrás que presionar el botón de bajar volumen dos veces!!",
                "Режим \"HoneyPot\" позволяет создать безопасную среду выполнения, которая имитирует NowiVault, не раскрывая ваши данные. Это отличный способ противодействия злоумышленнику, заставив его поверить, что он успешно получил доступ к вашей информации. ¡¡Чтобы выйти из этого режима, вам нужно будет дважды нажать кнопку уменьшения громкости!!",
                "\"HoneyPot\" 模式允許您創建一個模仿 NowiVault 的安全執行環境，而不會暴露您的數據。這是一種通過讓攻擊者相信他們已成功存取您的資訊來減輕攻擊的極佳方法。 ¡¡要退出此模式，您需要按兩下音量減小按鈕！！",
                "「HoneyPot」モードを使用すると、データを公開することなくNowiVaultを模倣する安全な実行環境を作成できます。攻撃者に情報へのアクセスに成功したと思わせることで、攻撃を緩和する優れた方法です。¡¡このモードを終了するには、音量下げボタンを2回押す必要があります！！"
            ),
            1 to data_idioms(
                "The \"CloseAll\" mode allows you to close NowiVault safely, ensuring that all values \u200B\u200B slated for local deletion are indeed removed.",
                "El modo \"CloseAll\" te permite cerrar NowiVault de forma segura, garantizando que todos los valores destinados a la eliminación local sean realmente eliminados.",
                "Режим \"CloseAll\" позволяет безопасно закрыть NowiVault, гарантируя, что все значения, предназначенные для локального удаления, действительно удалены.",
                "\"CloseAll\" 模式允許您安全地關閉 NowiVault，確保所有預定要在本地刪除的值都已被確實刪除。",
                "「CloseAll」モードを使用すると、NowiVaultを安全に閉じることができ、ローカル削除に予定されているすべての値が確実に削除されます。"
            ),
            2 to data_idioms(
                "The \"DeleteAll\" mode allows you to remove all of the app's local data; it is essentially a panic button. This deletion includes your cryptographic key (in any mode), your database, any settings, and all logs.",
                "El modo \"DeleteAll\" te permite eliminar todos los datos locales de la aplicación; es esencialmente un botón de pánico. Esta eliminación incluye tu clave criptográfica (en cualquier modo), tu base de datos, cualquier configuración y todos los registros.",
                "Режим \"DeleteAll\" позволяет удалить все локальные данные приложения; по сути, это тревожная кнопка. Это удаление включает ваш криптографический ключ (в любом режиме), вашу базу данных, любые настройки и все журналы.",
                "\"DeleteAll\" 模式允許您刪除應用程式的所有本地數據；它本質上是一個恐慌按鈕。此刪除包括您的加密金鑰（在任何模式下）、您的數據庫、任何設置和所有日誌。",
                "「DeleteAll」モードを使用すると、アプリのすべてのローカルデータを削除できます。これは本質的にパニックボタンです。この削除には、暗号化キー（任意のモード）、データベース、設定、すべてのログが含まれます。"
            ),
            3 to data_idioms(
                "The \"EncryptData\" mode allows you to encrypt your passwords if you have already decrypted them.",
                "El modo \"EncryptData\" te permite cifrar tus contraseñas si ya las has descifrado.",
                "Режим \"EncryptData\" позволяет зашифровать ваши пароли, если вы их уже расшифровали.",
                "\"EncryptData\" 模式允許您在已解密密碼的情況下對其進行加密。",
                "「EncryptData」モードを使用すると、既に復号されたパスワードを暗号化できます。"
            ),
            4 to data_idioms("Edit the icon and its functionalities", "Editar el icono y sus funcionalidades", "Редактировать значок и его функции", "編輯圖標及其功能", "アイコンとその機能を編集"),
            5 to data_idioms("icon", "icono", "значок", "圖標", "アイコン"),
            6 to data_idioms("Functionality", "Funcionalidad", "Функциональность", "功能", "機能"),
            7 to data_idioms("Explanation of the modes", "Explicación de los modos", "Объяснение режимов", "模式說明", "モードの説明"),
            8 to data_idioms("The mode:", "El modo:", "Режим:", "模式：", "モード："),
            9 to data_idioms("The button mode has been changed to", "El modo del botón ha sido cambiado a", "Режим кнопки изменен на", "按鈕模式已更改為", "ボタンモードが変更されました："),
            10 to data_idioms("secons", "segundos", "секунд", "秒", "秒"),
            11 to data_idioms("Your current timeout is", "Tu tiempo de espera actual es", "Ваш текущий тайм-аут", "您當前的超時時間為", "現在のタイムアウトは"),
            12 to data_idioms("NowiVault needs to restart to activate this configuration", "NowiVault necesita reiniciarse para activar esta configuración", "NowiVault необходимо перезапустить для активации этой конфигурации", "NowiVault 需要重新啟動以啟用此配置", "この設定を有効にするにはNowiVaultを再起動する必要があります"),
            13 to data_idioms("reset", "restablecer", "сброс", "重置", "リセット")
        )
    )

    fun select_idiom (posi: Int): String {
        val loca = idioms_list.getValue(block).getValue(posi)

        return when (pref.getString("idio", "en")) {
            "en" -> { loca.en }
            "es" -> { loca.es }
            "ru" -> { loca.ru }
            "ch" -> { loca.ch }
            else -> { loca.jp }
        }
    }
}
