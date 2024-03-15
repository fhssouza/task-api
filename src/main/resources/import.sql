INSERT INTO department (name) VALUES ('Financeiro');
INSERT INTO department (name) VALUES ('Comercial');
INSERT INTO department (name) VALUES ('Desenvolvimento');

INSERT INTO person (name, department_id ) VALUES ('Camila', 1);
INSERT INTO person (name, department_id ) VALUES ('Pedro', 2);
INSERT INTO person (name, department_id ) VALUES ('Fabiano', 3);
INSERT INTO person (name, department_id ) VALUES ('Raquel', 3);
INSERT INTO person (name, department_id ) VALUES ('Patricia', 3);
INSERT INTO person (name, department_id ) VALUES ('Joaquim', 3);

INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('101', 'Validar NF Janeiro', 'Validar notas recebidas no mês de Janeiro', '2022-02-15', 1, 14, 1, 'FINISHED');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('102', 'Bug 352', 'Corrigir bug 352 na versão 1.25', '2022-05-10', 3, 25, null, 'OPEN');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('103', 'Liberação da versão 1.24', 'Disponibilizar pacote para testes', '2022-02-02', 3, 25, 3, 'OPEN');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('104', 'Reunião', 'Reunião com o cliente A para apresentação do produto', '2022-02-05', 2, 5, null, 'OPEN');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('105', 'Reunião final', 'Fechamento contrato', '2022-03-28', 2, 6, null, 'OPEN');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('106', 'Pagamento 01/2022', 'Realizar pagamentos de fornecedores', '2022-01-31', 1, 6, 1, 'FINISHED');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('107', 'Bug 401', 'Corrigir bug 401 na versão 1.20', '2022-02-01', 3, 2, 4, 'FINISHED');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('108', 'Bug 399', 'Corrigir bug 399 na versão 1.20', '2022-01-28', 3, 6, 5, 'FINISHED');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('109', 'Reunião B', 'Reunião com o cliente B para apresentação do produto', '2022-01-31', 2, 5, 2, 'FINISHED');
INSERT INTO task (id, title, description, deadline, department_id, duration, person_id, task_status) VALUES ('110', 'Validar NF de Fevereiro', 'Validar notas recebidas no mês de Fevereiro', '2022-03-15', 1, 14, 6, 'OPEN');


