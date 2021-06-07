-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: fondomercosur
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ayuda`
--

DROP TABLE IF EXISTS `ayuda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ayuda` (
  `idayuda` bigint NOT NULL AUTO_INCREMENT,
  `clave` varchar(45) CHARACTER SET latin1 COLLATE latin1_spanish_ci DEFAULT NULL,
  `texto` varchar(5000) CHARACTER SET latin1 COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idayuda`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ayuda`
--

LOCK TABLES `ayuda` WRITE;
/*!40000 ALTER TABLE `ayuda` DISABLE KEYS */;
INSERT INTO `ayuda` VALUES (1,'fpar','El respaldo de parámetros incluye las siguientes tablas: </br>\n<b>parámetros, y parámetros históricos. </b><br/>\n\nSe recomienda efectuar el respaldo previamente a la modificación porque  esto es lo que permite volver atrás en caso de ser necesario, luego de hacer las modificaciones. </br>\n\nLuego el procedimiento de actualización se lleva a cabo en las solapas denominadas  \'Actualización MesLiquidación\' y \'Acualización Parámetro\'.</br>\nAl efectuar la actualización del MesLiquidación se ejecuta un procedimiento que copia los registros actuales de la tabla Parámetros a Parámetros históricos y luego actualiza el mesLiquidación en la tabla de parámetros tal como se mostrará, luego de ejecutado el procedimiento, en la solapa \'Lista\'.\n</br></br>\nUna vez efectuada la actualización del MesLiquidación se puede realizar la actualización de cualquiera de los parámetros que aparecen en la Lista seleccionando el icono de edición que figura en la última columna, en la línea correspondiente. </br>\n\nDe esta manera se accede a la solapa \'Actualización Parámetro\' en modo edición, donde se puede modificar cualquiera de los campos.\n</br></br>\nEs importante que<b> todos los meses se actualice el Mes Liquidación,</b> a los efectos de mantener la vinculación entre toda la información perteneciente a cada mes.\n'),(2,'fdist','Procedimiento de Distribución de Intereses. </br>\nLa información necesaria para efectuar la distribución de intereses es : </br>\n- el mes de comienzo del período de distribución </br>\n- el mes de fin del período de distribución </br>\n- el monto que se va a distribuir. </br>\n\nEventualmente, si hay algún funcionario que no deba se tenido en cuenta en la distribución deberá marcarse en la lista de los \"funcionarios que no participan en la distribución\". </br>\nEste procedimiento va a tomar en cuenta el mesLiquidación que figura en la tabla de parámetros. Por lo tanto se deberá chequear que sea el correcto antes de ejecutar el procedimiento. </br>\nLuego de ejecutado el procedimiento de distribución se mostrarán los resultados en la página y se podrán imprimir o guardar en formato pdf seleccionando el link(ícono) correspondiente.\n</br></br>\nA partir de este procedimiento y hasta el procedimiento del pago de cuotas los respaldos se efectúan sobre las siguientes tablas: </br>\n- parámetros, parámetros por funcionario, parámetros por cargas, </br>\n- adicionesydescuentofondo, movimientos, </br>\n- datos distribución intereses, resultados distribución intereses,</br>\n- préstamos, préstamosconsumo, préstamos históricos, préstamosconsumo históricos,</br>\n- saldos, saldosúltimomes, saldoshistoria, saldosacumulados de préstamos.\n\n'),(3,'faper','En este procedimiento de Apertura de Cuenta del Fondo de Previsión se debe ingresar el básico del primer porque en función de él se calcularán los aportes que generan los saldos iniciales de la cuenta.\nComo resultado de la ejecución del procedimiento se agrega un registro en la tabla Saldos, otro en la tabla de Movimientos y otro en la tabla de Adiciones y Descuentos del Fondo.\nSe DEBE EXCLUIR al funcionario en el siguiente cálculo de aportes del Fondo.'),(4,'fcierre','Procedimiento para el cierre de la cuenta del Fondo de Previsión. </br></br>\nEs importante realizar el respaldo previo porque de esa manera se podrá  restablecer la información, si fuese necesario rehacer el procedimiento.</br>\nEn la información solicitada es importante aclarar que el rango de meses que abarca el cálculo de intereses, deberá comenzar en el mes siguiente a la última distribución y finalizar en el mes en el que se produce egreso. Por ej. si la última distribución abarcó los meses de Agosto y Setiembre, y el funcionario se retira en Noviembre (sin importar qué día), el rango que se ingresará, será Octubre/Noviembre. </br>\nLuego de ingresar toda la información requerida se debe seleccionar el link \'Intereses y Préstamos Pendientes\' para que se realicen los cálculos necesarios. Como resultado de los cuales aparecerá el mensaje informativo con el monto que deberá abonarse al funcionario por concepto de intereses. </br>\nEs importante aclarar que hasta aquí no se ha grabado ninguna información en la base de datos, sino que se han realizado los cálculos en memoria. Para realizar efectivamente el cierre de la cuenta se deberá seleccionar el botón Check. </br>\nUna vez realizado el cierre se puede bajar la información a Excel (recomendado) o a Pdf.'),(5,'fsalr','A través de este procedimiento se actualizarán las remuneraciones por cargo/grado, de acuerdo con el porcentaje ingresado. </br>\n</br>\nEl monto del aumento quedará guardado como complemento porque así lo establece el reglamento para el cálculo de los aportes. </br>\n</br>\nCuando sea el momento de integrar dicho complemento, al salario, se deberá volver a utilizar este procedimiento y utilizar el link previsto a tales efectos.</br>\n</br>\nSi se intenta ejecutar el cálculo cuando aún no han sido integrados el básico y el complemento (o sea el valor del complemento es > 0), la app le mostrará un mensaje de error. </br>\n</br>\nSe recomienda efectuar el respaldo previo, porque si fuera necesario volver a ejecutar el cálculo para sobreescribir un cálculo anterior equivocado, lo correcto sería ejecutar una restauración para que los datos volvieran a la situación previa al cálculo erróneo y entonces sí sería posible volver a ejecutar el cálculo.\n\n'),(6,'fmut','Procedimiento previsto para realizar el mantenimiento de las mutualistas y sus planes. </br></br>\nEn el respaldo previo se incluyen las tablas: Mutualistas y MutualistasPlanes. </br></br>\nA nivel de cada mutualista se puede:</br>\n- ver cuales son los planes que tiene registrados;</br> \n- agregar un nuevo plan o </br>\n- eliminar una mutualistas. En este caso se controla que la mutualista que se quiere eliminar no esté asignada a ninguna persona registrada en la Base (ya sea funcionario o familiar). </br>\n</br>\nA nivel de los planes se puede: </br>\n- Modificar el valor de la cuota o </br>\n- Eliminar el plan siempre y cuando ninguna persona lo tenga asignado.\n'),(7,'fprstafa','Procedimiento previsto para el registro de los préstamos de AFALADI y/o del Seg. de Salud de AFALADI.</br></br>\nEn el respaldo previo se respaldan la tablas: préstamos Afaladi y evolución del préstamo. </br>\n\nEl procedimiento de registro del préstamo requiere el ingreso de toda la información establecida en el panel de Condiciones del Préstamo. </br>\nLa particularidad es que si el funcionario que solicita el préstamo ya tiene otro préstamo del mismo tipo en proceso, automáticamente este procedimiento lo llevara al procedimiento de cancelación, dado que no es posible otorgar más de un préstamo de cada tipo por funcionario. </br>\n</br>\nLuego de cancelado el préstamo pendiente se podrá ingresar, en la misma página, el préstamo nuevo y el sistema responderá informando de cuánto debe ser el importe del cheque a entregar.\n'),(8,'fprstafav','En esta página se presentará en primera instancia la lista de los préstamos vigentes y un link, en cada caso, que permite ver la tabla con la evolución del préstamo.\n</br>\nEn los casos de los préstamos ingresados, pero que todavía no han pagado ninguna cuota, también se habilitan los links para modificar sus condiciones y/o para eliminar el préstamo.'),(9,'formPrstAfa_1','De acuerdo a lo dispuesto en el \"Sistema de Préstamos\" de AFALADI, que declaro conocer y aceptar en todos sus términos, solicito un préstamo en las siguientes condiciones:'),(10,'formPrstAfa_2','Autorizo a la Sec. Gral. de ALADI a descontar las cuotas mensuales, por cuenta de AFALADI y en caso de cese de funciones en la Secretaría General de la ALADI, cualquiera fuere el motivo,  a descontar de los haberes correspondientes a mi liquidación final, el saldo impago de este préstamo.\n'),(11,'formPrstAfa_3','recibí cheque del Banco _______________________ N° ____________ por la suma de U$S ___________.\n'),(12,'formPrstAfaSS_1','De acuerdo a lo dispuesto en el \"Sistema Solidario de Salud\" de AFALADI, que declaro conocer y aceptar en todos sus términos, solicito un préstamo en las siguientes condiciones:'),(13,'frestinf','Este procedimiento está previsto para restaurar la información a la situación previa a cualquiera de los procedimientos incluidos en la lista del combo. </br>\nEl requerimiento esencial para que la recuperación sea correcta es que se haya efectuado el respaldo correspondiente en el procedimiento indicado. </br>\n</br>\nEn cada uno de los procedimientos existe la posibilidad de ejecutar un respaldo antes de ejecutarlo, y de ejecutar una recuperación posterior, deshaciendo lo hecho, es decir se vuelve al paso anterior. </br>\n\nEn cambio, en este procedimiento de Restablecimiento de la información, es posible volver más de un paso atrás. Siempre y cuando se hayan efectuado los respaldos previos en cada uno de los procedimientos ejecutados. </br>'),(14,'frespLHTables','Al seleccionar el link Respaldo Previo a Liquidación de Haberes se ejecuta un respaldo parcial en la Base de Datos que incluye las siguientes tablas: </br>\nFCabezalRecibos (donde se conserva la información de los últimos cabezal generados) </br>\nFHaberesLiquidados (donde se conserva la información de las líneas que componen el recibo de sueldo) </br>\nFAfaladiPrestamos (donde se conserva la información de los préstamos ingresados para el Fondo Común de AFALADI o el Seguro de Salud) </br>\nFCabezarecibosHist(donde se conserva la información histórica de los cabezales de cada mes)</br>\nFHaberesHist (donde se conserva la información histórica de las líneas de los recibos) </br>\nFAdpermanentes (donde se conserva la información de las adiciones y descuentos permanentes) </br>\n</br>\nal seleccionar el link Restaurar la información anterior, se ejecuta, en la Base de Datos, el procedimiento inverso al anterior. Por lo tanto se restaura la información existente en el último respaldo.'),(15,'finlistados','Se debe seleccionar una opción en el comboBox y solicitar la ejecución del procedimiento correspondiente a través del botón check. </br>\nAl seleccionar la opción Por Haber/Descuento se presentarán tanto el informe analítico como el de resumen. </br>\nLo mismo sucede cuando se selecciona la opción Afiliaciones por Instituciones de Salud. </br>\n'),(16,'frespmesSH','Aquí se presentan dos opciones que están relacionadas. </br>\n</br>\nEn el procedimiento de respaldo se guarda el estado de todas las tablas involucradas en los procedimientos del Fondo de Previsión de tal manera que si por alguna razón fuera necesario rehacer dichos procedimientos, ejecutando el procedimiento de restauración del mes anterior, se vuelve al punto inicial, permitiendo recomenzar el proceso. </br>\n\nLas tablas que se respaldan son: </br>\n- parámetros, parámetros históricos. </br>\n- adiciones y descuentos del fondo, saldos, saldos de préstamos, saldos del último mes, saldos históricos </br>\n- movimientos, préstamos, préstamos históricos. </br>\n- datos para la distribución de intereses y resultados de las distribuciones. </br></br>'),(17,'fprstafad','Se presenta la lista de los préstamos vigentes.\n</br>\nEn caso de que el préstamo no haya pagado ninguna cuota es posible eliminarlo y/o modificarlo. </br>\nDe lo contrario se deberá cancelar.'),(18,'factafi','Al seleccionar un funcionario se mostrarán las afiliaciones con pago compartido así como las afiliaciones por cuenta del funcionario, si las hubiera.</br>\nEn cualquiera de ambas listas es posible Eliminar o Modificar la información . </br>\nAdicionalmente, en el panel de afiliaciones con pago compartido, puede aparecer un link \'Agregar nueva afiliación\' si se trata de que, o bien el funcionario, o bien alguna de las cargas, no tenga asignada ninguna mutualista y plan. </br>\nA su vez, en el panel de Afiliaciones por cta. del funcionario, también aparece el link para agregar una afiliación nueva.</br>\nEn cualquier caso, ya sea por modificación o para agregar información el link \'Agregar nueva afiliación\' lo dirigirá a otra página en la que se ingresa la información necesaria en el panel \'Actualización de la información\'.'),(19,'fLiqEgreso','En la liquidación de Haberes por Egreso es importante realizar el Respaldo Previo, ya que habiéndolo realizado ud. podrá ejecutar la liquidación más de una vez (si fuese necesario). </br>\nPara volver a realizar una liquidación ya realizada es necesario restablecer la situación con la Restauración a la Situación Anterior. </br>\nPara ejecutar el procedimiento es necesario ingresar los datos solicitados y luego seleccionar el botón verde.</br>\nLuego, abriendo el panel inferior se presentará el recibo generado y en el margen inferior derecho de la página un link que le permite hacer las modificaciones necesarias en el recibo. </br>\nAl seguir el link \"modificar este recibo\" se accede a la página de módificación. En ella será posible eliminar, modificar o agregar una línea. </br>\nSi se trata de agregar una línea aparece una ventana donde ingresar la información de línea y confirmar su ingreso quedando salvada la nueva información. </br>\nSi se trata de eliminar una línea aparecerá la ventana pidiendo confirmación. Y si se trata de modificar, la información de la línea seleccionada pasará al área inferior de la página donde, una vez modificada la información se deberá seleccionar el botón verde para salvar la modificación. </br>\nUna vez realizadas todas la modificaciones necesarias, con el link \"volver\" que se encuentra en la esquina superior derecha de la página, se vuelve a la página de inicio del procedimiento desde donde se podrá generar el recibo en formato pdf. </br>\nEn esta página también se encuentra el link \"Pasaje a Históricos\".</br>\nCuando la información se pasa a los históricos desaparece de las tablas actuales. Las tablas afectadas son las de Costo Aladi, la de Movimientos, la de Haberes liquidados y el registro de Planta. </br>\nNo se guarda información de las cargas del funcionario que es eliminada automáticamente cuando se elimina el registro de Planta.'),(20,'festadoCta','Esta será la ayuda para la consulta del Estado de Cta.'),(21,'factbco','En esta página es posible consultar el modo de cobro de los funcionarios, así como actualizar la información respectiva o incluso ingresar un nuevo banco si fuera necesario.</br>\nAl seleccionar un funcionario se mostrará la información acerca de su modo de cobro, y de tratarse de transferencia, también aparecerá el nombre del Banco en el cual se realiza el depósito. </br></br>\nSi se quiere cambiar esta información, se deberá seleccionar la opción que corresponda en el panel inmediato inferior. Al seleccionar \"Transferencia\" se presentará la lista de los Bancos, donde se deberá seleccionar el indicado. Finalmente, para guardar las modificaciones se deberá seleccionar el botón verde que se encuentra en la misma línea. </br>\nSi el Banco al que se debe hacer la transferencia no figurase en la lista se deberá agregar en la caja de texto identificada con la etiqueta \"Agregar Banco\" y luego seleccionar el botón verde que se encuentra en el mismo panel. </br>'),(22,'fcancelPrst','Cancelación de Préstamos de la Cta. Individual. </br></br>\nComo en otros porcedimientos, se debe tener presente que realizando el Respaldo Previo es posible restablecer la situación si fuese necesario, para volver a ejecutar este procedimiento. </br></br>\nAl seleccionar un funcionario aparecerá la información de cuál es su capital disponible operable y la lista de préstamos pendientes. </br>\nSe podrá cancelar cualquiera (o eventualmente todos) de los préstamos que aparezcan en la tabla. </br></br>\nFinalmente destacar que desde aquí es posible acceder a la página para ingresar una nueva solicitud, a través del link \'Solicitud nueva\'. </br>\nAsimismo será posible volver o acceder a esta página desde la de solicitud nueva, a través del link \'Cancelación de Préstamos\'.\n'),(23,'fsolPrst','Solicitud de Préstamos. </br></br>\nEsta página se utiliza para ingresar una solicitud de préstamo de la cta. individual. </br>\nLuego de ingresada la información de: Capital, Plazo y Tasa,  se puede utilizar el botón \'Calcular Cuota\'  para saber cual será la cuota a pagar por el funcionario.</br>\n\nSe debe tener en cuenta que hasta este punto no se ha guardado ninguna información en la base de datos. Por lo tanto, hasta aquí se podrá ejecutar el cálculo todas las veces que sea necesario (por ej. cambiando el nro. de cuotas).</br></br>\nCuando se deba ingresar efectivamente el préstamo en la base de datos se deberá seleccionar el botón \'Ingresar Prst.\'.</br></br>\n\nEl préstamo ingresado queda guardado en la BD con la propiedad de \'prestamo nuevo = true\', con lo cual en el siguiente Pago de Cuotas no será incluído.</br>\n\nTambién será posible Editar o Eliminar dicho préstamo mientras tenga la propiedad de Prestamo Nuevo, es decir, en tanto no se haya pagado ninguna cuota. A posteriori del pago de cuota solamente se podrá cancelar el préstamo.</br>'),(24,'fsolNews','Solicitudes de Préstamos Nuevas. </br></br>\nEn esta página se presentan las solicitudes ingresadas que aún no han pagado ninguna cuota.</br>\nPor esta razón las solicitudes pueden ser eliminadas o modificadas. Y eso es lo que permite realizar este procedimiento.'),(25,'fprstVig','Esta página permite realizar la consulta sobre los préstamos vigentes, según sean de Consumo o sobre la Cta. Individual o por Cesión.</br>\nEn la opción de Menú \'Consultas Fondo Previsión\' es donde se encuentra la opción para imprimir el informe sobre detalle y resumen de préstamos.'),(26,'faportes','Procedimiento para registrar los aportes al Fondo de Previsión.</br></br>\nSe debe tener en cuenta que si existe algún funcionario ingresado con posterioridad a la última integración y no posee todavía una cuenta abierta en el Fondo de Previsión, se le deberá abrir la cuenta con el procedimiento previsto a tales efectos.</br>\nDicho funcionario, queda automáticamente excluído en este procedimiento, debido a que al abrirse la cuenta ya se calcularon sus capitales (disponible e integrado) en función del básico del 1er. mes.</br>\n\nEn caso de que algún otro funcionario no deba participar en este cálculo, deberá ser seleccionado en la lista que aparece en el panel \'Funcionarios que no participan .... \'.</br>\nPara seleccionar más de un funcionario de la lista se debe presionar la tecla Ctrl+Click.</br></br>\n\nLuego la única información necesaria para realizar el cálculo es el Mes Liquidación.</br>\nEl resultado del cálculo se presentará en pestaña identificada con: \'Resultado del cálculo de Integración\'.'),(27,'fpagoCuotas','Para ejecutar el pago de cuotas de los préstamos se deberá seleccionar si pagan todos o algún subconjunto no paga. </br>\nSi es el caso de que algún subconjunto no paga se deberá marcar cada caso.</br>\nFinalmente se debe ejecutar el pago tanto para los préstamos sobre la cuenta individual como para los préstamos de Consumo</br>'),(28,'rhImpMarcas','En este procedimiento se realiza la importación de las marcas contenidas en el reloj. <br/><br/>\nPara su correcta ejecución ES NECESARIO que el usuario ejecute el \"Respaldo Previo\" porque de esta forma, no sólo se respalda la situación tal como quedó en la lectura previa, sino que además se limpian las tablas temporales necesarias para la correcta transformación de los datos que se importarán. <br/><br/>\nUna vez realizado el respaldo previo el usuario deberá seleccionar la información que desea importar, que se encontrará en formato en TXT.\nLuego de ejecutado el procedimiento de importación, la aplicación devolverá el mensaje informando el éxito de la operación, o, en su defecto el mensaje de error que corresponda. </br></br>\nTambién se presentan en esta página los links necesarios para efectuar las tareas que están vinculadas a la importación, hasta el análisis de las mismas. </br>\n\"Ver las marcas incorporadas\" para confirmación de lo que ha sido traído. </br>\n\"Funcionarios sin Marcas\" mostrará la lista, si existe, de funcionarios que tienen ninguna marca en las fechas incorporadas y que no tienen registrada la ausencia que justifique esa falta de marcas. Desde esa página es posible acceder directamente a la página en la que se registran las ausencias. </br>\n\"Funcionarios con Marcas Impares\" muestra la lista correspondiente y además permite ver las últimas marcas leídas de los funcionarios en cuestión, para que sea posible efectuar las correcciones necesarias. </br>\n\"Analizar Marcas Incorporadas\" para realizar el análisis luego de que no haya más funcionarios \"Sin Marcas\" o \"Con Marcas Impares\". </br>\n\"Resultados del Análisis\" trae nuevamente las marcas analizadas.'),(29,'rhBoletas','A través de esta página podrá enviar una solicitud de salida por horas o de acreditación de horas extra. </br>\nLos pasos a dar para completar la solicitud son: </br>\n1 - Seleccionar la fecha y horas. Para eso, clickeando en el ícono de calendario podrá seleccionar día y hora. En particular, al clickear sobre el día se mostrará, en la parte inferior del control, la caja de texto con una hora. Para corregir la hora se debe clickear sobre ella, con lo cual se abrirá otra caja de diálogo donde podrá ingresar la hora deseada. Y por último, cuando los datos de día y hora sean los deseados, se debe clickear sobre \'Apply\' para finalizar.</br>\n2 - Seleccionar el tipo de solicitud del comboBox correspondiente.</br>\n3 - El Motivo solo es requerido cuando se trata de Misión de Servicio. </br>\n4 - Seleccionar al funcionario que debe autorizar la solicitud. </br>\n5 - Al confirmar el envío de la solicitud, con el botón check, el funcionario autorizante recibirá un mail de aviso y deberá ingresar a la aplicación para autorizarla y enviarla a Recursos Humanos para que sea procesada.</br>'),(30,'rhAutBol','Para autorizar o rechazar la solicitud debe seleccionar el botón \"Autorización\" que se encuentra en la línea correspondiente, con lo cual los datos de la solicitud se transfieren a la parte inferior de la página. </br>\nEn caso de autorizar la solicitud, deberá seleccionar luego el botón \"enviar a RRHH\", de lo contrario deberá seleccionar el botón \"devolver al Solicitante\". </br>\nEn el primer caso se enviará un mail de aviso a Recursos Humanos y ellos procesarán la solicitud. </br>\nEn el segundo caso se enviará un mail al solicitante con el aviso de rechazo.\n'),(31,'formLic_1','\"Los funcionarios presentarán al Sector Recursos Humanos sus solicitudes de licencia anual reglamentaria, debidamente autorizadas por los superiores. Salvo casos excepcionales, deberán presentarse con una antelación de tres (3) días hábiles anteriores a la fecha de salida\".'),(32,'formLic_2',' \"Es obligación del funcionario dejar constancia en el formulario de licencia de la dirección y teléfono donde sea posible localizarlo por razones de servicio. A su regreso, el funcionario deberá firmar la conformidad con el saldo de su licencia\".'),(33,'rhLicencia','A través de esta página podrá enviar una solicitud de licencia a su jefe, quien a su vez deberá derivarla al Sector Recursos Humanos para su procesamiento.</br>\nLos datos necesarios son: </br>\n- La fecha a partir de la cual desea tomar la licencia.\n- La fecha en la que se hallará de regreso.\n- La dirección durante su licencia así como el teléfono.'),(34,'rhHorarioGral','Procedimiento previsto para la actualización del Horaio General de la Secretaría. </br>\nTanto para ingresar la fecha desde, como la fecha hasta, se debe hacer click sobre el ícono de calendario previsto a tales efectos. </br>\nEn el calendario que aparece, se deberá marcar la fecha que corresponda. Al hacerlo, aparecerá debajo, la caja de texto con la hora 12:00. Se deberá hacer click sobre dicha caja de texto para determinar la hora correcta y luego seleccionar OK. </br>\nFinalmente se deberá seleccionar \"Apply\" para que quede firme la información de fecha y hora. (Repetir el procedimiento para la fecha/hora de salida). </br>\nCon el botón check se ingresa la información en la Base de Datos. </br>\nPara ver los horarios guardados en la Base de Datos se dispone del Link \"Consulta histórica\". </br>\nAl acceder a la información histórica, se podrá modificar la fecha de finalización del período en curso.'),(35,'rhFeriados','Procedimiento previsto para el mantenimiento de los días feriados, en la base de datos. </br>\nAl ingresar a la página, se verá la lista de los feriados registrados, ordenados desde la última fecha hacia atrás. </br>\nEn dicha lista se presentan además los links que permiten modificar o eliminar una fila. </br>\nTambién se presenta el link que permite agregar un nuevo feriado. Al seleccionar esta posibilidad se presentará el diálogo para ingresar la información relativa al nuevo feriado.\n'),(36,'rhCorrecMesAct','Procedimiento previsto para introducir las modificaciones necesarias en las marcas del mes. </br>\nSE RECOMIENDA EFECTUAR EL RESPALDO PREVIO. </br>\nAl seleccionar el funcionario que corresponda se mostrará la lista con sus marcas. En dicha lista se puede modificar o eliminar una fila determinada.</br>\nTambién es posible en este procedimiento ver información de Resumen, Agregar un nuevo registro y volver a Analizar las marcas (todas o las de una fecha en particular).\n'),(37,'rhCorrecMesAnt','Procedimiento previsto para introducir los Ajustes necesarios, en información ya procesada de meses anteriores. </br>\nSE RECOMIENDA HACER EL RESPALDO PREVIO. </br>\nDichos ajustes pueden referirse a ausencias ya registradas y que deban cambiar ya sea por el motivo de la misma o por el período comprendido (ajustes por días), o a algún cambio que deba registrarse en las marcas de meses anteriores que ya quedaron incorporados a los archivos de resumen y de actuación reglamentaria (ajustes por horas). </br>\nPor ejemplo sería el caso de horas extras cuya notificación es recibida por la of. de RH a posteriori de la realización del cierre mensual.</br>\nEn el caso del ajuste por horas, ES IMPORTANTE tener en cuenta que la información del mes anterior se trae por día y que se debe corregir toda la información que se deba corregir de ese día para luego ejecutar el procedimiento \"Realizar Ajustes\".</br>\n Por ej. si el funcionario tiene registrado \"tiempo extra sin reconocer\" al comienzo y al final de la jornada laboral, y ambos registros se deben cambiar a \"horas extras\", se deben hacer ambos cambios y luego seleccionar \"Realizar Ajustes\".'),(38,'rhCierre','Procedimiento previsto para la realización del Cierre Mensual.</br>\nSE RECOMIENDA EFECTUAR EL RESPALDO PREVIO.</br>\nSe debe introducir la fecha de Cierre y determinar su característica, es decir, si es Cierre Normal, Transitorio o Complementario. </br>\nLuego seleccionar el link \"Efectuar el Cierre\". </br>\nUna vez efectuado el cierre se presentarán los resultados en la parte inferior de la página. </br>\nSi dichos resultados no fueran los esperados, se deberá ejecutar la Restauración de la información al momento previo a la realización del cierre y luego de efectuar las correcciones que correspondiere se puede volver a ejecutar el cierre.</br>\nUna vez que esté conforme con los resultados arrojados por el procedimiento, se debe efectuar la \"Consolidación del Cierre\" porque este procedimiento es el que efectúa las operaciones que corresponda para actualizar la información de la Actuación Reglamentaria de los funcionarios además de otros procesos que se efectúan en ese momento, como por ej. el control de la edad de los hijos a cargo de los funcionarios, con el fin de determinar si alguno cumple los 21 años en ese mes.'),(39,'rhRespRest','En este procedimiento se realiza el respaldo y/o restauración  de todas las tablas de la base de datos que actualiza el módulo de RRHH.'),(40,'rhAusencias','Procedimiento previsto para el mantenimiento de las ausencias de los funcionarios, por el motivo que sea. </br>\nAl ingresar a esta página se presentará la lista de las ausencias registradas ordendas por fecha, desde la más avanzada a la más antigua. </br>\nEn la lista presentada se permite actualizar o eliminar cualquier fila. </br>\nTambién es posible realizar la consulta por funcionario o directamente acceder a ingresar un nuevo registro de ausencia.'),(41,'rhHorariosFun','Procedimiento previsto para registrar los horarios especiales que cumplan los funcionarios, ya sean estos con o sin salida y retorno intermedio.</br>\nAl ingreso se presentará la lista de los horarios especiales registrados en el sistema y se permite actualizar o eliminar cualquiera de las filas de la lista. </br>\nA su vez se presentan los links para agregar nuevos horarios o para realizar la consulta por funcionario. </br>'),(42,'rhLicencias','Procedimietno previsto para que la oficina de Recursos Humanos tenga la posibilidad de registrar una licencia si ésta no ha sido ingresada por el usuario utilizando el mecanismo previsto para esos fines.</br>\nAl ingresar a este procedimiento se presenta la lista de las licencias registradas para el último año. </br>\nEn el caso de las licencias ordinarias es posible generar el formulario pdf e imprimirlo.</br>\nTambién se proveen los links para ingresar una nueva licencia y para realizar la consulta por funcionario.'),(43,'rhCargas','En este procedimiento se debe seleccionar un funcionario, y al hacerlo se presentará la lista de familiares a cargo del funcionario así como cualquier situación de modificación que el sistema tenga registrada, por ej. si un hijo ha cumplido los 21 años y esa modificación todavía no ha sido procesada en la siguiente liquidación de sueldos.</br>\nTambién aquí se permite eliminar a cualquiera de los familiares a cargo, por ej. porque se acredite un divorcio, o modificar la información del familiar.</br>\nFinalmente, también es posible agregar un familiar, por ej. cuando se produce un nacimiento.'),(44,'fmovsFondo','Definiendo los meses de inicio y fin del período se mostrarán los movimientos de la cta. del Fondo de Previsión del funcionario.'),(45,'formPrstFP_1','De acuerdo a lo dispuesto en las Normas Generales de Personal, Reglamento del \"Fondo de Previsión\", que declaro conocer y aceptar en todos sus términos, solicito se me conceda un préstamo sobre mi cuenta individual, en las siguientes condiciones:'),(46,'formPrstFP_2','DEBERÁ PRESENTAR COMPROBANTES Y NO PODRÁ OPERAR HASTA NO RECOMPONER LA RESERVA.'),(47,'formPrstFP_3','Recibí cheque del Banco _________________________________ Nro. ____________'),(48,'formPrstFP_4',' por la suma de U$S _____________________.'),(49,'fsolPrstFondo','Solicitud de Préstamos del Fondo de Previsión. </br></br>\nEsta página se utiliza para ingresar una solicitud de préstamo, o bien de su cuenta individual, o bien de consumo. </br>\nEn  el panel correspondiente se podrá averiguar cual será el importe de la cuota a pagar, de acuerdo al capital solicitado, el interés elegido y la cantidad de cuotas. </br>\n</br>\nEn el caso de los préstamos de consumo no es posible elegir la tasa, ya que este tipo de préstamo tiene solamente una tasa definida que es del 6% anual. </br>\nPara obtener el valor de la cuota se debe utilizar el link \'cuota U$S\'. Se debe tener en cuenta que hasta este punto no se ha guardado ninguna información en la base de datos. Por lo tanto, hasta aquí se podrá ejecutar el cálculo todas las veces que sea necesario (por ej. cambiando el nro. de cuotas).</br></br>\nCuando se deba ingresar efectivamente la solicitud del préstamo en la base de datos se deberá seleccionar el botón Check. Y el sistema, además de registrar la solicitud en la Base de Datos, enviará mail a la administración del Fondo de Previsión para que la solicitud sea revisada y procesada como corresponda.</br>\n'),(50,'fsolPrstAfa','Solicitud de Préstamos de AFALADI.</br></br>\nEsta página se utiliza para ingresar una solicitud de préstamo a AFALADI. </br>\nPodrá ser del Fondo Común que AFALADI tiene reservado para cubrir algún caso de necesidad excepcional de sus asociados, o bien del Fondo Solidario de Salud de AFALADI. </br>\nEn el caso de los préstamos del Fondo Común de AFALADI, estos no pueden superar los U$S 400 y su devolución no puede exceder las 12 cuotas. </br></br>\nAl ingresar la solicitud se debe determinar el tipo de préstamo que se solicita, e ingresar además el capital y el nro. de cuotas. </br>\nAl igual que en las solicitudes de préstamos del Fondo de Previsión al utilizar el link \'cuota U$S\' se obtiene el valor de la cuota a pagar según el importe del capital y el nro. de cuotas. Cálculo que se podrá realizar todas las veces que se desee, variando alguna de las informaciones ingresadas.</br>\nEstos préstamos también tienen tasa fija, como los de consumo. </br></br>\n\nCuando se deba ingresar efectivamente la solicitud del préstamo en la base de datos se deberá seleccionar el botón Check. Y el sistema, además de registrar la solicitud en la Base de Datos, enviará mail a la administración del Fondo de Previsión para que la solicitud sea revisada y procesada como corresponda.</br>\n'),(51,'fPrstCons','Ingreso de Préstamo de Consumo.</br>\nSi el funcionario ha ingresado por el sistema, la solicitud correspondiente, al seleccionar el nombre le aparecerán en pantalla los datos de la solicitud.</br>\n'),(52,'formPrstCons_1','El que suscribe solicita se le conceda un préstamo de consumo, conforme a lo dispuesto en el Reglamento Administrativo.');
/*!40000 ALTER TABLE `ayuda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fadicionesdescf`
--

DROP TABLE IF EXISTS `fadicionesdescf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fadicionesdescf` (
  `idFAdicionesDescF` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int unsigned DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  `fcodigos_id` smallint NOT NULL,
  `Descripcion` varchar(1000) DEFAULT NULL,
  `SumaResta` smallint DEFAULT NULL,
  `Cantidad` smallint DEFAULT NULL,
  `Importe` decimal(8,2) DEFAULT NULL,
  `Moneda` varchar(45) DEFAULT NULL,
  `Registro` smallint DEFAULT NULL,
  `Orden` int DEFAULT NULL,
  PRIMARY KEY (`idFAdicionesDescF`),
  KEY `adicdesc_planta_idx` (`gplanta_id`),
  KEY `adicdesc_codigo_idx` (`fcodigos_id`),
  CONSTRAINT `adicdesc_codigo` FOREIGN KEY (`fcodigos_id`) REFERENCES `fcodigos` (`idFCodigos`),
  CONSTRAINT `adicdesc_planta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=1348 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fadicionesdescf`
--

LOCK TABLES `fadicionesdescf` WRITE;
/*!40000 ALTER TABLE `fadicionesdescf` DISABLE KEYS */;
INSERT INTO `fadicionesdescf` VALUES (1273,18,7894,'202006',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1274,19,2156,'202006',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1293,1,2935,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1294,2,435,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1295,3,695,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1296,4,860,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1297,5,905,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1298,6,1320,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1299,7,1465,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1300,8,1565,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1301,9,2000,'202005',69,'Apertura de Cuenta',2,1,0.00,'Dólares',NULL,NULL),(1302,2,435,'202006',69,'Aporte Funcionario al Fondo',2,1,714.00,'Dólares',NULL,NULL),(1303,3,695,'202006',69,'Aporte Funcionario al Fondo',2,1,497.00,'Dólares',NULL,NULL),(1304,4,860,'202006',69,'Aporte Funcionario al Fondo',2,1,406.00,'Dólares',NULL,NULL),(1305,5,905,'202006',69,'Aporte Funcionario al Fondo',2,1,315.00,'Dólares',NULL,NULL),(1306,6,1320,'202006',69,'Aporte Funcionario al Fondo',2,1,301.00,'Dólares',NULL,NULL),(1307,7,1465,'202006',69,'Aporte Funcionario al Fondo',2,1,301.00,'Dólares',NULL,NULL),(1308,8,1565,'202006',69,'Aporte Funcionario al Fondo',2,1,196.00,'Dólares',NULL,NULL),(1309,9,2000,'202006',69,'Aporte Funcionario al Fondo',2,1,126.00,'Dólares',NULL,NULL),(1310,1,2935,'202006',69,'Aporte Funcionario al Fondo',2,1,644.00,'Dólares',NULL,NULL),(1315,1,2935,'202007',69,'Aporte Funcionario al Fondo',2,1,644.00,'Dólares',NULL,NULL),(1316,2,435,'202007',69,'Aporte Funcionario al Fondo',2,1,714.00,'Dólares',NULL,NULL),(1317,3,695,'202007',69,'Aporte Funcionario al Fondo',2,1,497.00,'Dólares',NULL,NULL),(1318,4,860,'202007',69,'Aporte Funcionario al Fondo',2,1,406.00,'Dólares',NULL,NULL),(1319,5,905,'202007',69,'Aporte Funcionario al Fondo',2,1,315.00,'Dólares',NULL,NULL),(1320,6,1320,'202007',69,'Aporte Funcionario al Fondo',2,1,301.00,'Dólares',NULL,NULL),(1321,7,1465,'202007',69,'Aporte Funcionario al Fondo',2,1,301.00,'Dólares',NULL,NULL),(1322,8,1565,'202007',69,'Aporte Funcionario al Fondo',2,1,196.00,'Dólares',NULL,NULL),(1323,9,2000,'202007',69,'Aporte Funcionario al Fondo',2,1,126.00,'Dólares',NULL,NULL),(1324,18,7894,'202007',69,'Aporte Funcionario al Fondo',2,1,497.00,'Dólares',NULL,NULL),(1325,19,2156,'202007',69,'Aporte Funcionario al Fondo',2,1,196.00,'Dólares',NULL,NULL),(1326,1,2935,'202007',17,'Préstamos a Mediano Plazo',2,1,2000.00,'Dólares',NULL,NULL),(1327,2,435,'202007',17,'Préstamos a Mediano Plazo',2,1,3000.00,'Dólares',NULL,NULL),(1328,3,695,'202007',17,'Préstamos a Mediano Plazo',2,1,2000.00,'Dólares',NULL,NULL),(1329,1,2935,'202007',65,'1-Cuotas Prest. a Mediano Plazo',2,1,104.43,'Dólares',NULL,NULL),(1330,2,435,'202007',65,'2-Cuotas Prest. a Mediano Plazo',2,1,131.95,'Dólares',NULL,NULL),(1331,3,695,'202007',65,'3-Cuotas Prest. a Mediano Plazo',2,1,104.66,'Dólares',NULL,NULL),(1332,8,1565,'202008',17,'Préstamos a Mediano Plazo',2,1,1000.00,'Dólares',NULL,NULL),(1333,1,2935,'202008',69,'Aporte Funcionario al Fondo',2,1,644.00,'Dólares',NULL,NULL),(1334,2,435,'202008',69,'Aporte Funcionario al Fondo',2,1,714.00,'Dólares',NULL,NULL),(1335,3,695,'202008',69,'Aporte Funcionario al Fondo',2,1,497.00,'Dólares',NULL,NULL),(1336,4,860,'202008',69,'Aporte Funcionario al Fondo',2,1,406.00,'Dólares',NULL,NULL),(1337,5,905,'202008',69,'Aporte Funcionario al Fondo',2,1,315.00,'Dólares',NULL,NULL),(1338,6,1320,'202008',69,'Aporte Funcionario al Fondo',2,1,301.00,'Dólares',NULL,NULL),(1339,7,1465,'202008',69,'Aporte Funcionario al Fondo',2,1,301.00,'Dólares',NULL,NULL),(1340,8,1565,'202008',69,'Aporte Funcionario al Fondo',2,1,196.00,'Dólares',NULL,NULL),(1341,9,2000,'202008',69,'Aporte Funcionario al Fondo',2,1,126.00,'Dólares',NULL,NULL),(1342,18,7894,'202008',69,'Aporte Funcionario al Fondo',2,1,497.00,'Dólares',NULL,NULL),(1343,19,2156,'202008',69,'Aporte Funcionario al Fondo',2,1,196.00,'Dólares',NULL,NULL),(1344,1,2935,'202008',65,'1-Cuotas Prest. a Mediano Plazo',2,2,104.43,'Dólares',NULL,NULL),(1345,2,435,'202008',65,'2-Cuotas Prest. a Mediano Plazo',2,2,131.95,'Dólares',NULL,NULL),(1346,3,695,'202008',65,'3-Cuotas Prest. a Mediano Plazo',2,2,104.66,'Dólares',NULL,NULL),(1347,8,1565,'202008',65,'4-Cuotas Prest. a Mediano Plazo',2,1,52.33,'Dólares',NULL,NULL);
/*!40000 ALTER TABLE `fadicionesdescf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fcategoriaslog`
--

DROP TABLE IF EXISTS `fcategoriaslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fcategoriaslog` (
  `idfcategoriasLog` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(500) CHARACTER SET latin1 COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`idfcategoriasLog`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcategoriaslog`
--

LOCK TABLES `fcategoriaslog` WRITE;
/*!40000 ALTER TABLE `fcategoriaslog` DISABLE KEYS */;
INSERT INTO `fcategoriaslog` VALUES (1,'Actualización Parámetros'),(2,'Actualización Mes Liquidación'),(3,'Cálculo de Aportes'),(4,'Pago de Cuotas'),(5,'Distribución de Utilidades'),(6,'Apertura de Cuenta'),(7,'Cierre de Cuenta'),(8,'Solicitud Prestamo'),(9,'Respaldos/Restauraciones'),(10,'Creación-Actualización de Usuario'),(11,'Cancelación de Préstamo');
/*!40000 ALTER TABLE `fcategoriaslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fcodigos`
--

DROP TABLE IF EXISTS `fcodigos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fcodigos` (
  `idFCodigos` smallint NOT NULL AUTO_INCREMENT,
  `Codigo` smallint DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  `Adicion` tinyint(1) DEFAULT NULL,
  `Descuento` tinyint(1) DEFAULT NULL,
  `ColListLiquidos` smallint DEFAULT NULL,
  `Habilitado` tinyint(1) DEFAULT NULL,
  `Observaciones` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idFCodigos`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcodigos`
--

LOCK TABLES `fcodigos` WRITE;
/*!40000 ALTER TABLE `fcodigos` DISABLE KEYS */;
INSERT INTO `fcodigos` VALUES (1,1,'Sueldo Planta Internacional',1,0,1,1,NULL),(2,2,'Sueldo Planta General',1,0,1,1,NULL),(3,3,'Bonificación Vivienda',1,0,2,1,NULL),(4,4,'Bonificación Antigüedad',1,0,2,1,NULL),(5,5,'Aguinaldo',1,0,1,1,NULL),(6,6,'Bonificación familiar',1,0,2,1,NULL),(7,7,'Compensación',1,0,2,1,NULL),(8,8,'Aporte de Asociac. al Fondo',1,0,0,1,NULL),(9,9,'Adicional por ejercicio de Jefatura',1,0,2,1,NULL),(10,10,'Seguro de Salud',1,0,3,1,'Salud'),(11,11,'Seguro de Vida',1,0,0,1,NULL),(12,12,'Viajes de Servicio',1,0,0,1,NULL),(13,13,'Gastos de Representación',1,0,0,1,NULL),(14,14,'Adiciones Pendientes',1,0,4,1,NULL),(15,15,'Seguro de Accidentes de Trabajo',1,0,0,1,NULL),(16,16,'Préstamos a Corto Plazo',1,0,4,1,NULL),(17,17,'Préstamos a Mediano Plazo',1,0,4,1,NULL),(18,18,'Préstamos a Largo Plazo',1,0,4,1,NULL),(19,19,'Otros pagos del Fondo de Prev.',1,0,4,1,NULL),(20,20,'Pagos por Préstamos de consumo',1,0,4,1,NULL),(21,21,'Bonif. Esp. Planta Internacional (13er. Sueldo)',1,0,0,0,NULL),(22,22,'Horas Extras Dobles',1,0,2,1,NULL),(23,23,'Horas Extras Simples',1,0,2,1,NULL),(25,25,'Ajustes por Seguro de Salud',1,0,3,1,'Salud'),(26,26,'Bonificación Extraordinaria',1,0,0,1,NULL),(27,27,'Prest. Reestruct. Recap. 10% Sueldo',1,0,4,1,NULL),(28,28,'Intereses Fondo de Previsión',1,0,0,1,NULL),(29,29,'Pagos por Retiro Fdo. de Previsión',1,0,0,1,NULL),(30,30,'Indemnizaciones',1,0,0,1,NULL),(31,31,'Adelanto de Haberes',1,0,4,1,NULL),(32,32,'Liquidación Egreso',1,0,0,1,NULL),(33,33,'Adiciones Pendientes c/costo',1,0,0,1,NULL),(34,34,'Estímulos al desempeño',1,0,0,1,NULL),(35,35,'Bonificación Anual',1,0,0,1,NULL),(36,36,'AFALADI - Devolución',1,0,0,1,NULL),(37,37,'Licencia No Gozada',1,0,2,1,NULL),(50,50,'Banco Comercial',0,1,0,1,NULL),(51,51,'Cancelac. Adelanto de Haberes Dólares',0,1,4,1,NULL),(52,52,'Adelantos de Viáticos',0,1,0,1,NULL),(53,53,'ALICO Seguros de Vida',0,1,3,1,NULL),(54,54,'Seguro de Salud',0,1,3,1,'Salud'),(55,55,'Perses',0,1,4,1,'Salud'),(56,56,'Retención Judicial',0,1,4,1,NULL),(57,57,'A.F.A.L.A.D.I. Otros',0,1,4,1,NULL),(58,58,'Cooperativa Bancaria',0,1,4,1,NULL),(59,59,'Retenciones - Varios',0,1,4,1,NULL),(60,60,'Reintegro Otros Pagos p/cta. Func.',0,1,4,1,NULL),(61,61,'Inasistencia-Excesos At. Fliar.',0,1,4,1,NULL),(62,62,'Comunicaciones Telefónicas',0,1,4,1,NULL),(63,63,'Cuotas Prest. a Corto Plazo',0,1,4,1,NULL),(64,64,'Cancelac. Prest. a Corto Plazo',0,1,4,1,NULL),(65,65,'Cuotas Prest. a Mediano Plazo',0,1,4,1,NULL),(66,66,'Cancelación Prest. a Mediano Plazo',0,1,4,1,NULL),(67,67,'Cuotas Prest. a Largo Plazo',0,1,4,1,NULL),(68,68,'Cancelación Prest. a Largo Plazo',0,1,4,1,NULL),(69,69,'Aporte Funcionario al Fondo',0,1,3,1,NULL),(70,70,'Cancelación Adelantos Fondo',0,1,4,1,NULL),(72,72,'Cuota Prest. Reestruct. Recap.',0,1,4,1,NULL),(73,73,'Cancel. Prest. Reestruct. Recap.',0,1,4,1,NULL),(74,74,'AFALADI Seguro de Enfermedad',0,1,4,1,NULL),(75,75,'AFALADI Cuota Social',0,1,4,1,NULL),(76,76,'Desc. Por Préstamos de Consumo',0,1,4,1,NULL),(79,79,'Tickets médicos',0,1,4,1,''),(80,80,'Ajustes p/Seguro de Salud',0,1,4,1,'Salud'),(81,81,'SEMM',0,1,4,1,'Salud'),(82,82,'Seguro de Salud Otros',0,1,4,1,'Salud'),(83,83,'ITTHartFord - Extraprima por riesgo',0,1,0,1,NULL),(84,84,'Fotocopias',0,1,4,1,NULL),(86,86,'AFALADI Desc. por Préstamos',0,1,4,1,NULL),(87,87,'Cancel. Adelanto Bonificación Anual',0,1,0,1,NULL),(88,88,'DESCUENTO MEDIDA GREMIAL',0,1,4,1,NULL),(99,99,'Préstamo Especial',0,1,4,1,NULL),(100,100,'Traslado de enseres',1,0,NULL,1,NULL),(101,101,'Sin vinculación',0,0,0,1,NULL);
/*!40000 ALTER TABLE `fcodigos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fcuentaglobal`
--

DROP TABLE IF EXISTS `fcuentaglobal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fcuentaglobal` (
  `idFcuentaglobal` bigint NOT NULL AUTO_INCREMENT,
  `Fecha` date DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  `NroPrestamo` int DEFAULT NULL,
  `NroCuota` smallint DEFAULT NULL,
  `Importe` decimal(10,2) DEFAULT NULL,
  `Observaciones` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`idFcuentaglobal`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fcuentaglobal`
--

LOCK TABLES `fcuentaglobal` WRITE;
/*!40000 ALTER TABLE `fcuentaglobal` DISABLE KEYS */;
INSERT INTO `fcuentaglobal` VALUES (1,'2020-07-11','202007',1,1,8.33,NULL),(2,'2020-07-11','202007',2,1,13.13,NULL),(3,'2020-07-11','202007',3,1,8.75,NULL),(22,'2020-07-15','202008',1,2,7.93,NULL),(23,'2020-07-15','202008',2,2,12.61,NULL),(24,'2020-07-15','202008',3,2,8.33,NULL),(25,'2020-07-15','202008',4,1,4.38,NULL);
/*!40000 ALTER TABLE `fcuentaglobal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fdatosdistribintereses`
--

DROP TABLE IF EXISTS `fdatosdistribintereses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fdatosdistribintereses` (
  `idfdatosdistribintereses` bigint NOT NULL AUTO_INCREMENT,
  `MesDistrib` varchar(45) DEFAULT NULL,
  `ResultadoADistrib` decimal(10,2) DEFAULT NULL,
  `TotBaseDistrib` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idfdatosdistribintereses`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fdatosdistribintereses`
--

LOCK TABLES `fdatosdistribintereses` WRITE;
/*!40000 ALTER TABLE `fdatosdistribintereses` DISABLE KEYS */;
/*!40000 ALTER TABLE `fdatosdistribintereses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fmovimientos`
--

DROP TABLE IF EXISTS `fmovimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fmovimientos` (
  `idFMovimientos` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int unsigned DEFAULT NULL,
  `FechaMovimiento` date DEFAULT NULL,
  `Orden` bigint DEFAULT NULL,
  `ftipomovimiento_id` int NOT NULL,
  `codigoMovimiento` smallint DEFAULT NULL,
  `NroPrestamo` int DEFAULT NULL,
  `NroCuota` smallint DEFAULT NULL,
  `ImporteMov` decimal(10,2) DEFAULT NULL,
  `ImporteCapSec` decimal(10,2) DEFAULT NULL,
  `ImporteIntFunc` decimal(10,2) DEFAULT NULL,
  `SaldoAnterior` decimal(10,2) DEFAULT NULL,
  `SaldoActual` decimal(10,2) DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  `Observaciones` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idFMovimientos`),
  KEY `idx_movTarjeta_idx` (`Tarjeta`),
  KEY `mov_planta_idx` (`gplanta_id`),
  KEY `mov_tipomov_idx` (`ftipomovimiento_id`),
  CONSTRAINT `mov_planta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`),
  CONSTRAINT `mov_tipomov` FOREIGN KEY (`ftipomovimiento_id`) REFERENCES `ftipomovimiento` (`idFTipoMovimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=5025 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fmovimientos`
--

LOCK TABLES `fmovimientos` WRITE;
/*!40000 ALTER TABLE `fmovimientos` DISABLE KEYS */;
INSERT INTO `fmovimientos` VALUES (4949,18,7894,'2020-06-20',NULL,1,1,NULL,NULL,476.00,476.00,0.00,0.00,476.00,'202006','Apertura de Cuenta'),(4950,19,2156,'2020-06-21',NULL,1,1,NULL,NULL,210.00,210.00,0.00,0.00,210.00,'202006','Apertura de Cuenta'),(4970,1,2935,'2020-06-26',NULL,1,1,NULL,NULL,1288.00,1288.00,0.00,0.00,1288.00,'202005','Apertura de Cuenta'),(4971,2,435,'2020-06-26',NULL,1,1,NULL,NULL,1428.00,1428.00,0.00,0.00,1428.00,'202005','Apertura de Cuenta'),(4972,3,695,'2020-06-26',NULL,1,1,NULL,NULL,994.00,994.00,0.00,0.00,994.00,'202005','Apertura de Cuenta'),(4973,4,860,'2020-06-26',NULL,1,1,NULL,NULL,812.00,812.00,0.00,0.00,812.00,'202005','Apertura de Cuenta'),(4974,5,905,'2020-06-26',NULL,1,1,NULL,NULL,630.00,630.00,0.00,0.00,630.00,'202005','Apertura de Cuenta'),(4975,6,1320,'2020-06-26',NULL,1,1,NULL,NULL,602.00,602.00,0.00,0.00,602.00,'202005','Apertura de Cuenta'),(4976,7,1465,'2020-06-26',NULL,1,1,NULL,NULL,602.00,602.00,0.00,0.00,602.00,'202005','Apertura de Cuenta'),(4977,8,1565,'2020-06-26',NULL,1,1,NULL,NULL,392.00,392.00,0.00,0.00,392.00,'202005','Apertura de Cuenta'),(4978,9,2000,'2020-06-26',NULL,1,1,NULL,NULL,252.00,252.00,0.00,0.00,252.00,'202005','Apertura de Cuenta'),(4979,2,435,'2020-06-26',NULL,2,2,0,0,2142.00,1428.00,714.00,1428.00,3570.00,'202006','Aportes Mensuales'),(4980,3,695,'2020-06-26',NULL,2,2,0,0,1491.00,994.00,497.00,994.00,2485.00,'202006','Aportes Mensuales'),(4981,4,860,'2020-06-26',NULL,2,2,0,0,1218.00,812.00,406.00,812.00,2030.00,'202006','Aportes Mensuales'),(4982,5,905,'2020-06-26',NULL,2,2,0,0,945.00,630.00,315.00,630.00,1575.00,'202006','Aportes Mensuales'),(4983,6,1320,'2020-06-26',NULL,2,2,0,0,903.00,602.00,301.00,602.00,1505.00,'202006','Aportes Mensuales'),(4984,7,1465,'2020-06-26',NULL,2,2,0,0,903.00,602.00,301.00,602.00,1505.00,'202006','Aportes Mensuales'),(4985,8,1565,'2020-06-26',NULL,2,2,0,0,588.00,392.00,196.00,392.00,980.00,'202006','Aportes Mensuales'),(4986,9,2000,'2020-06-26',NULL,2,2,0,0,378.00,252.00,126.00,252.00,630.00,'202006','Aportes Mensuales'),(4987,1,2935,'2020-06-26',NULL,2,2,0,0,1932.00,1288.00,644.00,1288.00,3220.00,'202006','Aportes Mensuales'),(4992,1,2935,'2020-07-08',NULL,2,2,0,0,1932.00,1288.00,644.00,3220.00,5152.00,'202007','Aportes Mensuales'),(4993,2,435,'2020-07-08',NULL,2,2,0,0,2142.00,1428.00,714.00,3570.00,5712.00,'202007','Aportes Mensuales'),(4994,3,695,'2020-07-08',NULL,2,2,0,0,1491.00,994.00,497.00,2485.00,3976.00,'202007','Aportes Mensuales'),(4995,4,860,'2020-07-08',NULL,2,2,0,0,1218.00,812.00,406.00,2030.00,3248.00,'202007','Aportes Mensuales'),(4996,5,905,'2020-07-08',NULL,2,2,0,0,945.00,630.00,315.00,1575.00,2520.00,'202007','Aportes Mensuales'),(4997,6,1320,'2020-07-08',NULL,2,2,0,0,903.00,602.00,301.00,1505.00,2408.00,'202007','Aportes Mensuales'),(4998,7,1465,'2020-07-08',NULL,2,2,0,0,903.00,602.00,301.00,1505.00,2408.00,'202007','Aportes Mensuales'),(4999,8,1565,'2020-07-08',NULL,2,2,0,0,588.00,392.00,196.00,980.00,1568.00,'202007','Aportes Mensuales'),(5000,9,2000,'2020-07-08',NULL,2,2,0,0,378.00,252.00,126.00,630.00,1008.00,'202007','Aportes Mensuales'),(5001,18,7894,'2020-07-08',NULL,2,2,0,0,1491.00,994.00,497.00,476.00,1967.00,'202007','Aportes Mensuales'),(5002,19,2156,'2020-07-08',NULL,2,2,0,0,588.00,392.00,196.00,210.00,798.00,'202007','Aportes Mensuales'),(5003,1,2935,'2020-07-09',NULL,3,3,1,0,2000.00,0.00,0.00,5152.00,3152.00,'202007','Prestamo nuevo'),(5004,2,435,'2020-07-09',NULL,3,3,2,0,3000.00,0.00,0.00,5712.00,2712.00,'202007','Prestamo nuevo'),(5005,3,695,'2020-07-10',NULL,3,3,3,0,2000.00,0.00,0.00,3976.00,1976.00,'202007','Prestamo nuevo'),(5006,1,2935,'2020-07-11',NULL,4,4,1,1,104.43,96.10,8.33,3152.00,3256.43,'202007',NULL),(5007,2,435,'2020-07-11',NULL,4,4,2,1,131.95,118.82,13.13,2712.00,2843.95,'202007',NULL),(5008,3,695,'2020-07-11',NULL,4,4,3,1,104.66,95.91,8.75,1976.00,2080.66,'202007',NULL),(5009,8,1565,'2020-07-15',NULL,3,3,4,0,1000.00,0.00,0.00,1568.00,568.00,'202008','Prestamo nuevo'),(5010,1,2935,'2020-07-15',NULL,2,2,0,0,1932.00,1288.00,644.00,3256.43,5188.43,'202008','Aportes Mensuales'),(5011,2,435,'2020-07-15',NULL,2,2,0,0,2142.00,1428.00,714.00,2843.95,4985.95,'202008','Aportes Mensuales'),(5012,3,695,'2020-07-15',NULL,2,2,0,0,1491.00,994.00,497.00,2080.66,3571.66,'202008','Aportes Mensuales'),(5013,4,860,'2020-07-15',NULL,2,2,0,0,1218.00,812.00,406.00,3248.00,4466.00,'202008','Aportes Mensuales'),(5014,5,905,'2020-07-15',NULL,2,2,0,0,945.00,630.00,315.00,2520.00,3465.00,'202008','Aportes Mensuales'),(5015,6,1320,'2020-07-15',NULL,2,2,0,0,903.00,602.00,301.00,2408.00,3311.00,'202008','Aportes Mensuales'),(5016,7,1465,'2020-07-15',NULL,2,2,0,0,903.00,602.00,301.00,2408.00,3311.00,'202008','Aportes Mensuales'),(5017,8,1565,'2020-07-15',NULL,2,2,0,0,588.00,392.00,196.00,568.00,1156.00,'202008','Aportes Mensuales'),(5018,9,2000,'2020-07-15',NULL,2,2,0,0,378.00,252.00,126.00,1008.00,1386.00,'202008','Aportes Mensuales'),(5019,18,7894,'2020-07-15',NULL,2,2,0,0,1491.00,994.00,497.00,1967.00,3458.00,'202008','Aportes Mensuales'),(5020,19,2156,'2020-07-15',NULL,2,2,0,0,588.00,392.00,196.00,798.00,1386.00,'202008','Aportes Mensuales'),(5021,1,2935,'2020-07-15',NULL,4,4,1,2,104.43,96.50,7.93,5188.43,5292.86,'202008',NULL),(5022,2,435,'2020-07-15',NULL,4,4,2,2,131.95,119.34,12.61,4985.95,5117.90,'202008',NULL),(5023,3,695,'2020-07-15',NULL,4,4,3,2,104.66,96.33,8.33,3571.66,3676.32,'202008',NULL),(5024,8,1565,'2020-07-15',NULL,4,4,4,1,52.33,47.95,4.38,1156.00,1208.33,'202008',NULL);
/*!40000 ALTER TABLE `fmovimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fmovimientoshist`
--

DROP TABLE IF EXISTS `fmovimientoshist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fmovimientoshist` (
  `idFMovimientosHist` bigint NOT NULL AUTO_INCREMENT,
  `idGPlanta` bigint NOT NULL,
  `Tarjeta` int unsigned DEFAULT NULL,
  `FechaMovimiento` date DEFAULT NULL,
  `Orden` bigint DEFAULT NULL,
  `idFTipoMovimiento` int NOT NULL,
  `codigoMovimiento` smallint DEFAULT NULL,
  `NroPrestamo` int DEFAULT NULL,
  `NroCuota` smallint DEFAULT NULL,
  `ImporteMov` decimal(10,2) DEFAULT NULL,
  `ImporteCapSec` decimal(10,2) DEFAULT NULL,
  `ImporteIntFunc` decimal(10,2) DEFAULT NULL,
  `SaldoAnterior` decimal(10,2) DEFAULT NULL,
  `SaldoActual` decimal(10,2) DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  `Observaciones` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`idFMovimientosHist`),
  KEY `idx_movTarjeta_idx` (`Tarjeta`),
  KEY `MovPlanta_idx` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=54970 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fmovimientoshist`
--

LOCK TABLES `fmovimientoshist` WRITE;
/*!40000 ALTER TABLE `fmovimientoshist` DISABLE KEYS */;
/*!40000 ALTER TABLE `fmovimientoshist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fparametros`
--

DROP TABLE IF EXISTS `fparametros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fparametros` (
  `idfparametros` bigint NOT NULL AUTO_INCREMENT,
  `mesliquidacion` varchar(45) DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `simbolo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idfparametros`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fparametros`
--

LOCK TABLES `fparametros` WRITE;
/*!40000 ALTER TABLE `fparametros` DISABLE KEYS */;
INSERT INTO `fparametros` VALUES (1,'202008','Cotización Dólares',44.30,'$'),(3,'202008','Máximo Nro. de cuotas',84.00,''),(5,'202008','Pct de Reserva sobre Cap. Integrado',15.00,'%'),(6,'202008','Tope de afectación del sueldo base',40.00,'%'),(7,'202008','Cuotas a pagar previo operación',2.00,''),(22,'202008','Aporte patronal de MERECOSUR al Fondo (sobre sueldo + suplemento)',14.00,'%'),(23,'202008','Aporte funcionario al Fondo (sobre sueldo base)',7.00,'%');
/*!40000 ALTER TABLE `fparametros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fparametroshist`
--

DROP TABLE IF EXISTS `fparametroshist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fparametroshist` (
  `idfparametroshist` bigint NOT NULL AUTO_INCREMENT,
  `mesliquidacion` varchar(45) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  `descripcion` varchar(500) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  `valor` decimal(10,4) DEFAULT NULL,
  `simbolo` varchar(45) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`idfparametroshist`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fparametroshist`
--

LOCK TABLES `fparametroshist` WRITE;
/*!40000 ALTER TABLE `fparametroshist` DISABLE KEYS */;
INSERT INTO `fparametroshist` VALUES (1,'202005','Cotización Dólares',43.4000,'$'),(2,'202005','Máximo Nro. de cuotas',84.0000,''),(3,'202005','Pct de Reserva sobre Cap. Integrado',15.0000,'%'),(4,'202005','Tope de afectación del sueldo',40.0000,'%'),(5,'202005','Cuotas a pagar previo operación',2.0000,''),(6,'202005','Aporte  de MERECOSUR al Fondo (sobre sueldo + suplemento)',14.0000,'%'),(7,'202005','Aporte Funcionario al Fondo (sobre sueldo)',7.0000,'%'),(50,'202006','Cotización Dólares',43.4000,'$'),(51,'202006','Máximo Nro. de cuotas',84.0000,''),(52,'202006','Pct de Reserva sobre Cap. Integrado',15.0000,'%'),(53,'202006','Tope de afectación del sueldo',40.0000,'%'),(54,'202006','Cuotas a pagar previo operación',2.0000,''),(55,'202006','Aporte  de MERECOSUR al Fondo (sobre sueldo + suplemento)',14.0000,'%'),(56,'202006','Aporte Funcionario al Fondo (sobre sueldo)',7.0000,'%'),(57,'202007','Cotización Dólares',43.4000,'$'),(58,'202007','Máximo Nro. de cuotas',84.0000,''),(59,'202007','Pct de Reserva sobre Cap. Integrado',15.0000,'%'),(60,'202007','Tope de afectación del sueldo',40.0000,'%'),(61,'202007','Cuotas a pagar previo operación',2.0000,''),(62,'202007','Aporte  de MERECOSUR al Fondo (sobre sueldo + suplemento)',14.0000,'%'),(63,'202007','Aporte Funcionario al Fondo (sobre sueldo)',7.0000,'%'),(64,'202006','Cotización Dólares',44.3000,'$'),(65,'202006','Máximo Nro. de cuotas',84.0000,''),(66,'202006','Pct de Reserva sobre Cap. Integrado',15.0000,'%'),(67,'202006','Tope de afectación del sueldo base',40.0000,'%'),(68,'202006','Cuotas a pagar previo operación',2.0000,''),(69,'202006','Aporte patronal de MERECOSUR al Fondo (sobre sueldo + suplemento)',14.0000,'%'),(70,'202006','Aporte funcionario al Fondo (sobre sueldo base)',7.0000,'%'),(71,'202007','Cotización Dólares',44.3000,'$'),(72,'202007','Máximo Nro. de cuotas',84.0000,''),(73,'202007','Pct de Reserva sobre Cap. Integrado',15.0000,'%'),(74,'202007','Tope de afectación del sueldo base',40.0000,'%'),(75,'202007','Cuotas a pagar previo operación',2.0000,''),(76,'202007','Aporte patronal de MERECOSUR al Fondo (sobre sueldo + suplemento)',14.0000,'%'),(77,'202007','Aporte funcionario al Fondo (sobre sueldo base)',7.0000,'%');
/*!40000 ALTER TABLE `fparametroshist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fprestamos`
--

DROP TABLE IF EXISTS `fprestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fprestamos` (
  `idFPrestamos` bigint NOT NULL AUTO_INCREMENT,
  `NroPrestamo` int DEFAULT NULL,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `FechaPrestamo` date DEFAULT NULL,
  `CapitalPrestamo` decimal(10,2) DEFAULT NULL,
  `ftipoPrestamo_id` int NOT NULL,
  `CodigoPrestamo` smallint DEFAULT NULL,
  `InteresPrestamo` decimal(6,2) DEFAULT NULL,
  `Cuota` decimal(6,2) DEFAULT NULL,
  `CantCuotas` smallint DEFAULT NULL,
  `CuotasPagas` smallint DEFAULT NULL,
  `SaldoPrestamo` decimal(10,2) DEFAULT NULL,
  `FechaSaldo` date DEFAULT NULL,
  `PrestamoNuevo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idFPrestamos`),
  UNIQUE KEY `idx_Prest` (`NroPrestamo`),
  KEY `idx_tipoPrest_idx` (`CodigoPrestamo`),
  KEY `PrestTipo_idx` (`ftipoPrestamo_id`),
  KEY `PrestPlanta_idx` (`gplanta_id`),
  CONSTRAINT `PrestPlanta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`),
  CONSTRAINT `PrestTipo` FOREIGN KEY (`ftipoPrestamo_id`) REFERENCES `ftipoprestamo` (`idFTipoPrestamo`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fprestamos`
--

LOCK TABLES `fprestamos` WRITE;
/*!40000 ALTER TABLE `fprestamos` DISABLE KEYS */;
INSERT INTO `fprestamos` VALUES (167,1,1,2935,'2020-07-09',2000.00,11,2,5.00,104.43,20,2,1807.40,'2020-07-15',0),(168,2,2,435,'2020-07-09',3000.00,11,2,5.25,131.95,24,2,2761.84,'2020-07-15',0),(169,3,3,695,'2020-07-10',2000.00,11,2,5.25,104.66,20,2,1807.76,'2020-07-15',0),(170,4,8,1565,'2020-07-15',1000.00,11,2,5.25,52.33,20,1,952.05,'2020-07-15',0);
/*!40000 ALTER TABLE `fprestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fprestamoshist`
--

DROP TABLE IF EXISTS `fprestamoshist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fprestamoshist` (
  `idFPrestamosHist` bigint NOT NULL AUTO_INCREMENT,
  `NroPrestamo` int DEFAULT NULL,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `FechaPrestamo` date DEFAULT NULL,
  `CapitalPrestamo` decimal(10,2) DEFAULT NULL,
  `ftipoprestamo_id` int DEFAULT NULL,
  `CodigoPrestamo` smallint DEFAULT NULL,
  `InteresPrestamo` decimal(6,2) DEFAULT NULL,
  `Cuota` decimal(6,2) DEFAULT NULL,
  `CantCuotas` smallint DEFAULT NULL,
  `CuotasPagas` smallint DEFAULT NULL,
  `SaldoPrestamo` decimal(10,2) DEFAULT NULL,
  `FechaSaldo` date DEFAULT NULL,
  `PrestamoNuevo` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idFPrestamosHist`),
  UNIQUE KEY `idx_Prest` (`NroPrestamo`),
  KEY `idx_tipPrestH_idx` (`CodigoPrestamo`),
  KEY `idx_tipPrestH_idx1` (`ftipoprestamo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1831 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fprestamoshist`
--

LOCK TABLES `fprestamoshist` WRITE;
/*!40000 ALTER TABLE `fprestamoshist` DISABLE KEYS */;
/*!40000 ALTER TABLE `fprestamoshist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fprocedimientos`
--

DROP TABLE IF EXISTS `fprocedimientos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fprocedimientos` (
  `idfprocedimientos` int NOT NULL,
  `Descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idfprocedimientos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fprocedimientos`
--

LOCK TABLES `fprocedimientos` WRITE;
/*!40000 ALTER TABLE `fprocedimientos` DISABLE KEYS */;
INSERT INTO `fprocedimientos` VALUES (1,'Distribución de Intereses'),(2,'Cierre de Cuenta'),(3,'Préstamos'),(4,'Aportes'),(5,'Pago de cuotas');
/*!40000 ALTER TABLE `fprocedimientos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fresultadosdistribucion`
--

DROP TABLE IF EXISTS `fresultadosdistribucion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fresultadosdistribucion` (
  `idFResultadosDistribucion` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `MesInicial` varchar(45) DEFAULT NULL,
  `MesFinal` varchar(45) DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  `fcodigos_id` smallint DEFAULT NULL,
  `TotalADistribuir` decimal(12,2) DEFAULT NULL,
  `NumeralesFuncionario` decimal(10,2) DEFAULT NULL,
  `NumeralesTodos` decimal(12,2) DEFAULT NULL,
  `PctFuncionario` decimal(6,3) DEFAULT NULL,
  `DistribucionFuncionario` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`idFResultadosDistribucion`),
  KEY `RedisPlanta_idx` (`gplanta_id`),
  KEY `RedisCodigo_idx` (`fcodigos_id`),
  CONSTRAINT `RedisCodigo` FOREIGN KEY (`fcodigos_id`) REFERENCES `fcodigos` (`idFCodigos`),
  CONSTRAINT `RedisPlanta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=23854 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fresultadosdistribucion`
--

LOCK TABLES `fresultadosdistribucion` WRITE;
/*!40000 ALTER TABLE `fresultadosdistribucion` DISABLE KEYS */;
/*!40000 ALTER TABLE `fresultadosdistribucion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fsaldos`
--

DROP TABLE IF EXISTS `fsaldos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fsaldos` (
  `idFSaldos` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int unsigned NOT NULL,
  `Fecha` date DEFAULT NULL,
  `CapitalIntegAntes` decimal(10,2) DEFAULT NULL,
  `CapitalIntegActual` decimal(10,2) DEFAULT NULL,
  `CapitalDispAntes` decimal(10,2) DEFAULT NULL,
  `CapitalDispActual` decimal(10,2) DEFAULT NULL,
  `Numerales` decimal(10,2) DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idFSaldos`),
  KEY `Saldos_planta_idx` (`gplanta_id`),
  CONSTRAINT `Saldos_planta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fsaldos`
--

LOCK TABLES `fsaldos` WRITE;
/*!40000 ALTER TABLE `fsaldos` DISABLE KEYS */;
INSERT INTO `fsaldos` VALUES (248,18,7894,'2020-07-15',3458.00,3458.00,3458.00,3458.00,5901.00,'202008'),(249,19,2156,'2020-07-15',1386.00,1386.00,1386.00,1386.00,2394.00,'202008'),(265,1,2935,'2020-07-15',7084.00,7084.00,5276.60,5276.60,11744.70,'202008'),(266,2,435,'2020-07-15',7854.00,7854.00,5092.16,5092.16,11492.98,'202008'),(267,3,695,'2020-07-15',5467.00,5467.00,3659.24,3659.24,8216.15,'202008'),(268,4,860,'2020-07-15',4466.00,4466.00,4466.00,4466.00,9744.00,'202008'),(269,5,905,'2020-07-15',3465.00,3465.00,3465.00,3465.00,7560.00,'202008'),(270,6,1320,'2020-07-15',3311.00,3311.00,3311.00,3311.00,7224.00,'202008'),(271,7,1465,'2020-07-15',3311.00,3311.00,3311.00,3311.00,7224.00,'202008'),(272,8,1565,'2020-07-15',2156.00,2156.00,1203.95,1203.95,3751.95,'202008'),(273,9,2000,'2020-07-15',1386.00,1386.00,1386.00,1386.00,3024.00,'202008');
/*!40000 ALTER TABLE `fsaldos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fsaldoshistoria`
--

DROP TABLE IF EXISTS `fsaldoshistoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fsaldoshistoria` (
  `idFSaldosHistoria` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `CapitalIntegAntes` decimal(10,2) DEFAULT NULL,
  `CapitalIntegActual` decimal(10,2) DEFAULT NULL,
  `CapitalDispAntes` decimal(10,2) DEFAULT NULL,
  `CapitalDispActual` decimal(10,2) DEFAULT NULL,
  `Numerales` decimal(10,2) DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  `Motivo` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`idFSaldosHistoria`),
  KEY `idx_Func` (`Tarjeta`,`Fecha`)
) ENGINE=InnoDB AUTO_INCREMENT=10362 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fsaldoshistoria`
--

LOCK TABLES `fsaldoshistoria` WRITE;
/*!40000 ALTER TABLE `fsaldoshistoria` DISABLE KEYS */;
INSERT INTO `fsaldoshistoria` VALUES (10340,18,7894,'2020-07-08',476.00,1967.00,476.00,1967.00,2443.00,'202007',NULL),(10341,19,2156,'2020-07-08',210.00,798.00,210.00,798.00,1008.00,'202007',NULL),(10342,1,2935,'2020-07-11',3220.00,5152.00,3220.00,3248.10,6468.10,'202007',NULL),(10343,2,435,'2020-07-11',3570.00,5712.00,3570.00,2830.82,6400.82,'202007',NULL),(10344,3,695,'2020-07-11',2485.00,3976.00,2485.00,2071.91,4556.91,'202007',NULL),(10345,4,860,'2020-07-08',2030.00,3248.00,2030.00,3248.00,5278.00,'202007',NULL),(10346,5,905,'2020-07-08',1575.00,2520.00,1575.00,2520.00,4095.00,'202007',NULL),(10347,6,1320,'2020-07-08',1505.00,2408.00,1505.00,2408.00,3913.00,'202007',NULL),(10348,7,1465,'2020-07-08',1505.00,2408.00,1505.00,2408.00,3913.00,'202007',NULL),(10349,8,1565,'2020-07-08',980.00,1568.00,980.00,1568.00,2548.00,'202007',NULL),(10350,9,2000,'2020-07-08',630.00,1008.00,630.00,1008.00,1638.00,'202007',NULL),(10351,18,7894,'2020-07-15',1967.00,3458.00,1967.00,3458.00,5901.00,'202008',NULL),(10352,19,2156,'2020-07-15',798.00,1386.00,798.00,1386.00,2394.00,'202008',NULL),(10353,1,2935,'2020-07-15',5152.00,7084.00,3248.10,5276.60,11744.70,'202008',NULL),(10354,2,435,'2020-07-15',5712.00,7854.00,2830.82,5092.16,11492.98,'202008',NULL),(10355,3,695,'2020-07-15',3976.00,5467.00,2071.91,3659.24,8216.15,'202008',NULL),(10356,4,860,'2020-07-15',3248.00,4466.00,3248.00,4466.00,9744.00,'202008',NULL),(10357,5,905,'2020-07-15',2520.00,3465.00,2520.00,3465.00,7560.00,'202008',NULL),(10358,6,1320,'2020-07-15',2408.00,3311.00,2408.00,3311.00,7224.00,'202008',NULL),(10359,7,1465,'2020-07-15',2408.00,3311.00,2408.00,3311.00,7224.00,'202008',NULL),(10360,8,1565,'2020-07-15',1568.00,2156.00,1568.00,1203.95,3751.95,'202008',NULL),(10361,9,2000,'2020-07-15',1008.00,1386.00,1008.00,1386.00,3024.00,'202008',NULL);
/*!40000 ALTER TABLE `fsaldoshistoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fsaldosprstacum`
--

DROP TABLE IF EXISTS `fsaldosprstacum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fsaldosprstacum` (
  `idFSaldosPrstAcum` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `SaldoPrestAcumulado` decimal(10,2) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  PRIMARY KEY (`idFSaldosPrstAcum`),
  UNIQUE KEY `idx_Func` (`Tarjeta`),
  KEY `SacumPlanta_idx` (`gplanta_id`),
  CONSTRAINT `SacumPlanta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fsaldosprstacum`
--

LOCK TABLES `fsaldosprstacum` WRITE;
/*!40000 ALTER TABLE `fsaldosprstacum` DISABLE KEYS */;
INSERT INTO `fsaldosprstacum` VALUES (56,1,2935,1807.40,'2020-07-15'),(57,2,435,2761.84,'2020-07-15'),(58,3,695,1807.76,'2020-07-15'),(59,8,1565,952.05,'2020-07-15');
/*!40000 ALTER TABLE `fsaldosprstacum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fsaldosultimomes`
--

DROP TABLE IF EXISTS `fsaldosultimomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fsaldosultimomes` (
  `idFSaldosUltimoMes` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `CapitalIntegAntes` decimal(10,2) DEFAULT NULL,
  `CapitalIntegActual` decimal(10,2) DEFAULT NULL,
  `CapitalDispAntes` decimal(10,2) DEFAULT NULL,
  `CapitalDispActual` decimal(10,2) DEFAULT NULL,
  `Numerales` decimal(10,2) DEFAULT NULL,
  `MesLiquidacion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idFSaldosUltimoMes`),
  UNIQUE KEY `idx_Func` (`Tarjeta`),
  KEY `sultmes_planta_idx` (`gplanta_id`),
  CONSTRAINT `sultmes_planta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=830 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fsaldosultimomes`
--

LOCK TABLES `fsaldosultimomes` WRITE;
/*!40000 ALTER TABLE `fsaldosultimomes` DISABLE KEYS */;
INSERT INTO `fsaldosultimomes` VALUES (819,18,7894,'2020-07-15',1967.00,3458.00,1967.00,3458.00,5901.00,'202008'),(820,19,2156,'2020-07-15',798.00,1386.00,798.00,1386.00,2394.00,'202008'),(821,1,2935,'2020-07-15',5152.00,7084.00,3248.10,5276.60,11744.70,'202008'),(822,2,435,'2020-07-15',5712.00,7854.00,2830.82,5092.16,11492.98,'202008'),(823,3,695,'2020-07-15',3976.00,5467.00,2071.91,3659.24,8216.15,'202008'),(824,4,860,'2020-07-15',3248.00,4466.00,3248.00,4466.00,9744.00,'202008'),(825,5,905,'2020-07-15',2520.00,3465.00,2520.00,3465.00,7560.00,'202008'),(826,6,1320,'2020-07-15',2408.00,3311.00,2408.00,3311.00,7224.00,'202008'),(827,7,1465,'2020-07-15',2408.00,3311.00,2408.00,3311.00,7224.00,'202008'),(828,8,1565,'2020-07-15',1568.00,2156.00,1568.00,1203.95,3751.95,'202008'),(829,9,2000,'2020-07-15',1008.00,1386.00,1008.00,1386.00,3024.00,'202008');
/*!40000 ALTER TABLE `fsaldosultimomes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fsolicitudprestamos`
--

DROP TABLE IF EXISTS `fsolicitudprestamos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fsolicitudprestamos` (
  `idFSolicitud` int NOT NULL AUTO_INCREMENT,
  `idGPlanta` bigint NOT NULL,
  `Tarjeta` int DEFAULT NULL,
  `FechaSolicitud` date DEFAULT NULL,
  `CapitalPrestamo` decimal(10,2) NOT NULL,
  `idFTipoPrestamo` int DEFAULT NULL,
  `TipoPrestamo` smallint NOT NULL,
  `Moneda` varchar(45) DEFAULT NULL,
  `InteresPrestamo` decimal(6,2) NOT NULL,
  `Cuota` decimal(6,2) NOT NULL,
  `CantCuotas` smallint NOT NULL,
  `CesionTarjeta` smallint DEFAULT NULL,
  `CancelaPrstNros` varchar(50) DEFAULT NULL,
  `CancelaPrstConsumo` varchar(45) DEFAULT NULL,
  `ImporteNeto` decimal(10,2) DEFAULT NULL,
  `EnviadaAFondo` tinyint DEFAULT NULL,
  `EnviadaAComision` tinyint DEFAULT NULL,
  `EnviadaAFinanzas` tinyint DEFAULT NULL,
  `Procesada` tinyint DEFAULT NULL,
  `Motivo` varchar(1500) DEFAULT NULL,
  PRIMARY KEY (`idFSolicitud`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fsolicitudprestamos`
--

LOCK TABLES `fsolicitudprestamos` WRITE;
/*!40000 ALTER TABLE `fsolicitudprestamos` DISABLE KEYS */;
/*!40000 ALTER TABLE `fsolicitudprestamos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fsueldomes`
--

DROP TABLE IF EXISTS `fsueldomes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fsueldomes` (
  `idFSueldoMes` bigint NOT NULL AUTO_INCREMENT,
  `gplanta_id` bigint NOT NULL,
  `Tarjeta` int unsigned NOT NULL,
  `AnioMes` varchar(6) NOT NULL,
  `Fecha` date NOT NULL,
  `Sueldomes` decimal(8,2) NOT NULL,
  `Motivo` varchar(100) NOT NULL,
  PRIMARY KEY (`idFSueldoMes`),
  UNIQUE KEY `idx_Func` (`Tarjeta`,`AnioMes`),
  KEY `sueldomes_planta_idx` (`gplanta_id`),
  CONSTRAINT `sueldomes_planta` FOREIGN KEY (`gplanta_id`) REFERENCES `gplanta` (`idGPlanta`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fsueldomes`
--

LOCK TABLES `fsueldomes` WRITE;
/*!40000 ALTER TABLE `fsueldomes` DISABLE KEYS */;
INSERT INTO `fsueldomes` VALUES (21,18,7894,'202006','2020-06-20',3400.00,'apertura de cuenta'),(22,19,2156,'202006','2020-06-21',1500.00,'apertura de cuenta'),(38,1,2935,'202005','2020-06-26',9200.00,'apertura de cuenta'),(39,2,435,'202005','2020-06-26',10200.00,'apertura de cuenta'),(40,3,695,'202005','2020-06-26',7100.00,'apertura de cuenta'),(41,4,860,'202005','2020-06-26',5800.00,'apertura de cuenta'),(42,5,905,'202005','2020-06-26',4500.00,'apertura de cuenta'),(43,6,1320,'202005','2020-06-26',4300.00,'apertura de cuenta'),(44,7,1465,'202005','2020-06-26',4300.00,'apertura de cuenta'),(45,8,1565,'202005','2020-06-26',2800.00,'apertura de cuenta'),(46,9,2000,'202005','2020-06-26',1800.00,'apertura de cuenta');
/*!40000 ALTER TABLE `fsueldomes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ftipomovimiento`
--

DROP TABLE IF EXISTS `ftipomovimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ftipomovimiento` (
  `idFTipoMovimiento` int NOT NULL AUTO_INCREMENT,
  `codigoMovimiento` smallint DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFTipoMovimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ftipomovimiento`
--

LOCK TABLES `ftipomovimiento` WRITE;
/*!40000 ALTER TABLE `ftipomovimiento` DISABLE KEYS */;
INSERT INTO `ftipomovimiento` VALUES (1,1,'Apertura de Cuenta'),(2,2,'Aportes mensuales'),(3,3,'Solicitud Prestamo'),(4,4,'Pago de Cuotas '),(5,5,'Cancelación de Préstamo'),(6,6,'Asignación de intereses por Colocaciones'),(7,7,'Cierre de Cuenta'),(8,8,'Modificación de Solicitud de Préstamo'),(9,9,'Sin determinar');
/*!40000 ALTER TABLE `ftipomovimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ftipoprestamo`
--

DROP TABLE IF EXISTS `ftipoprestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ftipoprestamo` (
  `idFTipoPrestamo` int NOT NULL AUTO_INCREMENT,
  `CodigoPrestamo` smallint DEFAULT NULL,
  `Descripcion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFTipoPrestamo`),
  UNIQUE KEY `idx_TipoP` (`CodigoPrestamo`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ftipoprestamo`
--

LOCK TABLES `ftipoprestamo` WRITE;
/*!40000 ALTER TABLE `ftipoprestamo` DISABLE KEYS */;
INSERT INTO `ftipoprestamo` VALUES (10,1,'Corto Plazo - hasta 12 cuotas'),(11,2,'Mediano Plazo - entre 13 y 60 cuotas'),(12,3,'Largo Plazo - más de 60 cuotas');
/*!40000 ALTER TABLE `ftipoprestamo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ggrados`
--

DROP TABLE IF EXISTS `ggrados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ggrados` (
  `idggrados` int NOT NULL AUTO_INCREMENT,
  `nomgrado` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  `basico` decimal(10,2) DEFAULT NULL,
  `complemento` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`idggrados`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ggrados`
--

LOCK TABLES `ggrados` WRITE;
/*!40000 ALTER TABLE `ggrados` DISABLE KEYS */;
INSERT INTO `ggrados` VALUES (1,'Director Gral.',10200.00,0.00),(2,'Coordinador Gral.',9200.00,0.00),(3,'Coordinador Ejecutivo FOCEM',7100.00,0.00),(5,'Técnico Senior',5800.00,0.00),(6,'Analista de Proyectos',4500.00,0.00),(7,'Técnico',4300.00,0.00),(8,'Asistente Técnico',2800.00,0.00),(9,'Personal de Apoyo',1800.00,0.00);
/*!40000 ALTER TABLE `ggrados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gplanta`
--

DROP TABLE IF EXISTS `gplanta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gplanta` (
  `idGPlanta` bigint NOT NULL AUTO_INCREMENT,
  `Tarjeta` int unsigned NOT NULL,
  `Nombre` varchar(30) DEFAULT NULL,
  `Documento` varchar(20) DEFAULT NULL,
  `Nacionalidad` varchar(15) DEFAULT NULL,
  `Ingreso` date DEFAULT NULL,
  `Sexo` char(1) DEFAULT NULL,
  `ggrados_id` int DEFAULT NULL,
  `ultimoIngreso` tinyint DEFAULT NULL,
  `fotourl` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idGPlanta`),
  UNIQUE KEY `idxTarjeta` (`Tarjeta`),
  KEY `planta_grado_idx` (`ggrados_id`),
  CONSTRAINT `planta_grado` FOREIGN KEY (`ggrados_id`) REFERENCES `ggrados` (`idggrados`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gplanta`
--

LOCK TABLES `gplanta` WRITE;
/*!40000 ALTER TABLE `gplanta` DISABLE KEYS */;
INSERT INTO `gplanta` VALUES (1,2935,'Adriana Ferraro','3.220.130-1','uruguaya','2020-01-01','F',2,0,NULL),(2,435,'Rubens Andreani','1.239.821-5','uruguayo','1997-07-15','M',1,0,NULL),(3,695,'Silvana Asteggiante','1.837.121-7','Uruguay','1999-08-16','F',3,0,NULL),(4,860,'Cristina Baletti','1.348.968-9','Uruguay','1991-04-01','F',5,0,NULL),(5,905,'Sabina Barone','2.987.625-0','Uruguay','2012-08-01','F',6,0,NULL),(6,1320,'Claudia Bouissa','3.238.015-3','Uruguay','2000-09-18','F',7,0,NULL),(7,1465,'Pedor Calvache','MRE AT 1145 R','Ecuador','1993-12-13','M',7,0,NULL),(8,1565,'Dianela Cansani','3.268.456-9','Uruguay','1994-09-16','F',8,0,NULL),(9,2000,'Yhony Céspedes','3.427.276-6','Uruguay','1991-04-01','M',9,0,NULL),(18,7894,'Verónica Rios','3.220.145-8','Uruguaya','2020-06-15','F',3,0,NULL),(19,2156,'Carmen Da Silveira','4.235.687-7','Uruguaya','2020-06-08','F',8,0,NULL);
/*!40000 ALTER TABLE `gplanta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logfondo`
--

DROP TABLE IF EXISTS `logfondo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logfondo` (
  `idlogfondo` bigint NOT NULL AUTO_INCREMENT,
  `fcategoriaslog_id` int DEFAULT NULL,
  `procedimiento` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  `fechahora` datetime NOT NULL,
  `username` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`idlogfondo`),
  KEY `logfondo_categoria_idx` (`fcategoriaslog_id`),
  CONSTRAINT `logfondo_categoria` FOREIGN KEY (`fcategoriaslog_id`) REFERENCES `fcategoriaslog` (`idfcategoriasLog`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logfondo`
--

LOCK TABLES `logfondo` WRITE;
/*!40000 ALTER TABLE `logfondo` DISABLE KEYS */;
INSERT INTO `logfondo` VALUES (1,10,'Creación de Usuario: yhony','2020-05-28 14:19:20','aferraro'),(2,1,'Se agregó un Parámetro - Máximo de cuotas para los préstamos','2020-05-28 14:20:21','aferraro'),(3,1,'Se eliminó el  Parámetro - Cotización Dólar062020','2020-06-02 13:13:12','aferraro'),(4,1,'Actualización de Parámetros - Cotización Dólares','2020-06-02 13:13:33','aferraro'),(5,1,'Se eliminó el  Parámetro - Máximo de cuotas para los préstamos062020','2020-06-02 13:14:09','aferraro'),(6,1,'Actualización de Parámetros - Máximo Nro. de cuotas','2020-06-02 13:14:47','aferraro'),(7,1,'Actualización de Parámetros - Pct de Reserva sobre Cap. Integrado','2020-06-02 13:15:15','aferraro'),(8,1,'Actualización de Parámetros - Tope de afectación del sueldo','2020-06-02 13:16:14','aferraro'),(9,1,'Actualización de Parámetros - Cuotas a pagar previo operación','2020-06-02 13:17:03','aferraro'),(10,1,'Se eliminó el  Parámetro - Tope de afectación del sueldo062020','2020-06-02 13:17:23','aferraro'),(11,1,'Se eliminó el  Parámetro - Porcentaje de Reserva062020','2020-06-02 13:25:06','aferraro'),(12,1,'Se eliminó el  Parámetro - Tasa Préstamos Consumo012020','2020-06-02 14:12:39','aferraro'),(13,1,'Actualización de Parámetros - Pct. aporte SSAFALADI funcionarios','2020-06-02 14:17:06','aferraro'),(14,1,'Se eliminó el  Parámetro - Tasa Préstamos Fondo Salud AFALADI012020','2020-06-02 14:18:55','aferraro'),(15,1,'Se eliminó el  Parámetro - Tasa Préstamos AFALADI012020','2020-06-02 14:26:28','aferraro'),(16,1,'Se eliminó el  Parámetro - Tope Capital préstamos AFALADI012020','2020-06-02 14:50:15','aferraro'),(17,1,'Se eliminó el  Parámetro - Cuota Seguro de Vida > 65 años012020','2020-06-02 14:56:08','aferraro'),(18,1,'Se eliminó el  Parámetro - Compensación Secretaria SGA012020','2020-06-02 14:57:43','aferraro'),(19,1,'Se eliminó el  Parámetro - Ultimo Nro. de Préstamo012020','2020-06-02 15:00:04','aferraro'),(20,1,'Se eliminó el  Parámetro - Compensación Secretaria SG012020','2020-06-02 15:00:21','aferraro'),(21,1,'Se eliminó el  Parámetro - Valor Cápita FONASA012020','2020-06-02 15:00:36','aferraro'),(22,1,'Se eliminó el  Parámetro - Compensación por Jefatura012020','2020-06-02 15:00:51','aferraro'),(23,1,'Se eliminó el  Parámetro - Último Nro de Préstamo Consumo012020','2020-06-02 15:01:02','aferraro'),(24,1,'Se eliminó el  Parámetro - Pct. Capital Disponible012020','2020-06-02 15:01:37','aferraro'),(25,1,'Se eliminó el  Parámetro - Cuota Seguro Accidentes de Trabajo012020','2020-06-02 15:02:59','aferraro'),(26,1,'Se eliminó el  Parámetro - Pct. aporte SSAFALADI funcionarios012020','2020-06-02 15:17:17','aferraro'),(27,1,'Se eliminó el  Parámetro - Cuota Seguro de Vida012020','2020-06-02 15:17:24','aferraro'),(28,1,'Actualización de Parámetros - Aporte  de MERECOSUR sobre sueldo + suplemento, al Fondo','2020-06-02 15:18:25','aferraro'),(29,1,'Actualización de Parámetros - Aporte  de MERECOSUR al fondo (sobre sueldo + suplemento)','2020-06-02 15:18:54','aferraro'),(30,1,'Actualización de Parámetros - Aporte  de MERECOSUR al Fondo (sobre sueldo + suplemento)','2020-06-02 15:19:14','aferraro'),(31,1,'Actualización de Parámetros - Aporte Funcionario al Fondo (sobre sueldo)','2020-06-02 15:19:29','aferraro'),(32,10,'Cambio de Password del Usuario: yhony','2020-06-04 15:29:57','aferraro'),(33,10,'Cambio de Password del Usuario: yhony','2020-06-04 19:35:44','aferraro'),(34,10,'Cambio de Password del Usuario: sasteggiante','2020-06-05 17:26:14','aferraro'),(35,1,'Actualización de Parámetros - Cotización Dólares','2020-06-06 19:13:53','aferraro'),(36,1,'Actualización de Parámetros - Cuotas a pagar previo operación','2020-06-07 14:12:25','aferraro'),(37,1,'Actualización de Parámetros - Cuotas a pagar previo operación','2020-06-07 14:12:36','aferraro'),(38,6,'Apertura de Cuenta','2020-06-21 12:53:55','aferraro'),(39,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-06-24 17:16:16','aferraro'),(40,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-06-24 17:31:05','aferraro'),(41,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-06-24 17:35:02','aferraro'),(42,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restmesPrevio.bat','2020-06-24 17:43:10','aferraro'),(43,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:43:47','aferraro'),(44,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:43:56','aferraro'),(45,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:48:05','aferraro'),(46,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:51:36','aferraro'),(47,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:51:45','aferraro'),(48,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:51:59','aferraro'),(49,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:52:20','aferraro'),(50,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:52:30','aferraro'),(51,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:54:23','aferraro'),(52,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:54:29','aferraro'),(53,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:54:39','aferraro'),(54,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:54:42','aferraro'),(55,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:55:01','aferraro'),(56,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:55:10','aferraro'),(57,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:55:21','aferraro'),(58,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:55:34','aferraro'),(59,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respParametros.bat','2020-06-24 17:56:59','aferraro'),(60,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:57:07','aferraro'),(61,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restParametros.bat','2020-06-24 17:57:16','aferraro'),(62,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-06-24 17:58:20','aferraro'),(63,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-06-24 17:58:33','aferraro'),(64,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restmesPrevio.bat','2020-06-24 17:59:02','aferraro'),(65,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-06-25 13:11:29','aferraro'),(66,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-06-25 13:12:32','aferraro'),(67,6,'Apertura de Cuenta','2020-06-26 11:11:33','aferraro'),(68,6,'Apertura de Cuenta','2020-06-26 11:18:54','aferraro'),(69,6,'Apertura de Cuenta','2020-06-26 11:19:48','aferraro'),(70,6,'Apertura de Cuenta','2020-06-26 11:20:04','aferraro'),(71,6,'Apertura de Cuenta','2020-06-26 11:21:29','aferraro'),(72,6,'Apertura de Cuenta','2020-06-26 11:21:32','aferraro'),(73,6,'Apertura de Cuenta','2020-06-26 11:21:34','aferraro'),(74,6,'Apertura de Cuenta','2020-06-26 11:21:36','aferraro'),(75,6,'Apertura de Cuenta','2020-06-26 11:21:38','aferraro'),(76,3,'Cálculo de Aportes','2020-06-26 12:24:13','aferraro'),(77,6,'Apertura de Cuenta','2020-06-26 16:22:53','aferraro'),(78,6,'Apertura de Cuenta','2020-06-26 16:22:56','aferraro'),(79,6,'Apertura de Cuenta','2020-06-26 16:22:58','aferraro'),(80,6,'Apertura de Cuenta','2020-06-26 16:23:01','aferraro'),(81,6,'Apertura de Cuenta','2020-06-26 16:23:05','aferraro'),(82,6,'Apertura de Cuenta','2020-06-26 16:23:08','aferraro'),(83,6,'Apertura de Cuenta','2020-06-26 16:23:10','aferraro'),(84,6,'Apertura de Cuenta','2020-06-26 16:23:12','aferraro'),(85,6,'Apertura de Cuenta','2020-06-26 16:23:14','aferraro'),(86,3,'Cálculo de Aportes','2020-06-26 16:23:28','aferraro'),(87,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respSalarios.bat','2020-06-29 17:47:11','aferraro'),(88,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respSalarios.bat','2020-06-29 17:47:47','aferraro'),(89,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restSalarios.bat','2020-06-29 17:55:38','aferraro'),(90,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restSalarios.bat','2020-06-29 18:23:46','aferraro'),(91,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respSalarios.bat','2020-06-29 18:25:56','aferraro'),(92,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restSalarios.bat','2020-06-29 18:26:28','aferraro'),(93,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restSalarios.bat','2020-06-29 18:27:07','aferraro'),(94,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restSalarios.bat','2020-06-30 11:29:12','aferraro'),(97,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-06-30 18:38:23','aferraro'),(98,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-06-30 18:39:32','aferraro'),(99,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-06-30 18:42:18','aferraro'),(100,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 14:57:47','aferraro'),(101,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 14:57:48','aferraro'),(102,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:00:40','aferraro'),(103,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:00:40','aferraro'),(104,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:02:07','aferraro'),(105,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:02:07','aferraro'),(106,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:02:39','aferraro'),(107,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:02:39','aferraro'),(108,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:04:22','aferraro'),(109,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:04:22','aferraro'),(110,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:06:24','aferraro'),(111,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:06:24','aferraro'),(112,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:07:06','aferraro'),(113,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:07:06','aferraro'),(114,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:23:04','aferraro'),(115,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:23:04','aferraro'),(116,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:28:47','aferraro'),(117,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:28:47','aferraro'),(118,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:30:28','aferraro'),(119,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:30:28','aferraro'),(120,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:33:03','aferraro'),(121,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:33:03','aferraro'),(122,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:36:41','aferraro'),(123,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:36:41','aferraro'),(124,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:50:28','aferraro'),(125,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:50:28','aferraro'),(126,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 15:53:33','aferraro'),(127,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 15:53:33','aferraro'),(128,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 16:04:42','aferraro'),(129,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 16:04:43','aferraro'),(130,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 18:35:39','aferraro'),(131,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 18:59:58','aferraro'),(132,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:00:07','aferraro'),(133,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:00:20','aferraro'),(134,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 19:00:25','aferraro'),(135,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:00:40','aferraro'),(136,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 19:00:55','aferraro'),(137,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:05:29','aferraro'),(138,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:05:44','aferraro'),(139,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:06:51','aferraro'),(140,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-04 19:07:00','aferraro'),(141,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-04 19:38:15','aferraro'),(142,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-05 18:39:53','aferraro'),(143,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-05 18:50:20','aferraro'),(144,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-05 18:50:38','aferraro'),(145,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-05 18:50:48','aferraro'),(146,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-05 18:59:32','aferraro'),(147,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-05 19:12:58','aferraro'),(148,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-06 11:06:00','aferraro'),(149,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-06 11:06:26','aferraro'),(150,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-06 11:20:13','aferraro'),(151,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-06 11:28:14','aferraro'),(152,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-06 12:58:20','aferraro'),(153,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-06 12:58:36','aferraro'),(154,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-06 13:05:54','aferraro'),(155,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-07 17:16:14','aferraro'),(156,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-07 17:27:14','aferraro'),(157,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-08 14:32:17','aferraro'),(158,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 14:53:00','aferraro'),(159,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-08 15:28:01','aferraro'),(160,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 2','2020-07-08 16:04:33','aferraro'),(161,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 3','2020-07-08 16:05:30','aferraro'),(162,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 4','2020-07-08 16:09:46','aferraro'),(164,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-08 17:04:59','aferraro'),(165,3,'Cálculo de Aportes','2020-07-08 17:05:07','aferraro'),(166,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 17:06:59','aferraro'),(167,3,'Cálculo de Aportes','2020-07-08 17:07:27','aferraro'),(168,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 17:19:21','aferraro'),(169,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 17:21:50','aferraro'),(170,3,'Cálculo de Aportes','2020-07-08 17:22:27','aferraro'),(171,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-08 17:31:19','aferraro'),(172,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 17:32:35','aferraro'),(173,3,'Cálculo de Aportes','2020-07-08 17:33:08','aferraro'),(174,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 17:45:26','aferraro'),(175,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-08 17:47:23','aferraro'),(176,3,'Cálculo de Aportes','2020-07-08 17:47:37','aferraro'),(177,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 17:48:04','aferraro'),(178,3,'Cálculo de Aportes','2020-07-08 17:54:09','aferraro'),(179,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 18:11:25','aferraro'),(180,3,'Cálculo de Aportes','2020-07-08 18:11:35','aferraro'),(181,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 18:13:23','aferraro'),(182,3,'Cálculo de Aportes','2020-07-08 18:16:06','aferraro'),(183,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-08 18:18:51','aferraro'),(184,3,'Cálculo de Aportes','2020-07-08 19:11:51','aferraro'),(185,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-08 19:21:34','aferraro'),(186,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-09 19:23:53','aferraro'),(187,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 1','2020-07-09 19:24:48','aferraro'),(188,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 2','2020-07-09 19:26:34','aferraro'),(189,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 3','2020-07-10 18:13:21','aferraro'),(190,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-11 16:09:27','aferraro'),(191,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 16:16:17','aferraro'),(192,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-11 16:35:52','aferraro'),(193,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 16:36:47','aferraro'),(194,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 16:47:33','aferraro'),(195,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 16:50:23','aferraro'),(196,4,'Pago de Cuotas - Finalizó exitosamente incluye: Consolidación de Saldos','2020-07-11 16:51:01','aferraro'),(197,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 17:05:46','aferraro'),(198,4,'Pago de Cuotas - Finalizó exitosamente incluye: Consolidación de Saldos','2020-07-11 17:06:16','aferraro'),(199,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-11 17:11:05','aferraro'),(200,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 17:12:57','aferraro'),(201,4,'Pago de Cuotas - Finalizó exitosamente incluye: Consolidación de Saldos','2020-07-11 17:12:57','aferraro'),(202,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-11 17:25:37','aferraro'),(203,4,'Pago de Cuotas - 202007finalizó exitosamente','2020-07-11 17:26:14','aferraro'),(204,4,'Pago de Cuotas - Finalizó exitosamente incluye: Consolidación de Saldos','2020-07-11 17:26:14','aferraro'),(205,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restmesPrevio.bat','2020-07-12 12:13:45','aferraro'),(206,1,'Actualización Parámetros -  Mes Liquidación 202008','2020-07-15 17:37:02','aferraro'),(207,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-15 17:45:33','aferraro'),(208,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-15 19:04:15','aferraro'),(209,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-15 19:05:47','aferraro'),(210,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-15 19:06:07','aferraro'),(211,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-15 19:06:32','aferraro'),(212,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\restIntermedio.bat ','2020-07-15 19:06:38','aferraro'),(213,8,'Solicitud Prestamo - Se ingresó exitosamente. Prst nro: 4','2020-07-15 19:16:00','aferraro'),(214,3,'Cálculo de Aportes','2020-07-15 19:16:44','aferraro'),(215,9,'Respaldos/Restauraciones - Proceso externo - Ejecución exitosa: C:\\var\\backup\\respIntermedio.bat ','2020-07-15 19:17:49','aferraro'),(216,4,'Pago de Cuotas - 202008finalizó exitosamente','2020-07-15 19:18:01','aferraro'),(217,4,'Pago de Cuotas - Finalizó exitosamente la Consolidación de Saldos','2020-07-15 19:18:01','aferraro');
/*!40000 ALTER TABLE `logfondo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id_rol` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (10,'ROLE_ADMIN','ADMIN'),(11,'ROLE_USER','USER'),(12,'ROLE_SUPERVISOR','SUPERVISOR');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `idGPlanta` bigint DEFAULT NULL,
  `tarjeta` int DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Gplanta-Usuario_idx` (`idGPlanta`),
  CONSTRAINT `Gplanta-Usuario` FOREIGN KEY (`idGPlanta`) REFERENCES `gplanta` (`idGPlanta`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,2935,'Adriana','Ferraro','aferraro','$2a$04$qrRFornQDyjSsu4mnFrhjOYSMhAvE6kA4FHD2AUgtz13zR/YJQy66','aferraro@gmail.com'),(2,2,435,'Rubens','Andreani','randreani','$2a$04$71UPJzrg9pazPjYgLDllS.q.JGALsCamM65K2oc4kn0/F6trsrUHa','randreani@gmail.com'),(3,3,695,'Silvana','Asteggiante','sasteggiante','$2a$04$VZizeYxEMNlmaJVDQ84u1O4zDlDZSnf097Sjsc2t0EghwGWy5kGxa','silvana@gmail.com'),(4,9,2000,'Yhony','Céspedes','yhony','$2a$04$uw8J5SDEF0Q8Vw5iHlEQT.d98zkOmPOS5qUoT4Wadlj2AbqwqwZvu','yhony@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_rol` (
  `idusuario_rol` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  `id_rol` bigint NOT NULL,
  PRIMARY KEY (`idusuario_rol`),
  KEY `userRol_idx` (`id_usuario`),
  KEY `rolUser_idx` (`id_rol`),
  CONSTRAINT `rolUser` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`),
  CONSTRAINT `userRol` FOREIGN KEY (`id_usuario`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_rol`
--

LOCK TABLES `usuario_rol` WRITE;
/*!40000 ALTER TABLE `usuario_rol` DISABLE KEYS */;
INSERT INTO `usuario_rol` VALUES (103,2,11),(104,1,10),(105,3,11),(106,4,11);
/*!40000 ALTER TABLE `usuario_rol` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-16 16:54:37
