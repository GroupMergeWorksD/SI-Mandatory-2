INSERT INTO equipment (id, name, status) VALUES
('8f6c7bbd-3f1f-4b6f-9bcb-84db99411f2a', 'Projector A', 'ACTIVE'),
('e3e4f267-5e88-4505-bad1-4a99c2b6050d', 'Projector B', 'ACTIVE'),
('9a822a38-9151-4431-86f7-22ee16a0df86', 'Camera Kit A', 'ACTIVE'),
('e24f6d98-645d-40f9-8f49-25ef49fb5d08', 'Camera Kit B', 'ACTIVE'),
('f8b0f702-9c34-497a-9d61-57e235f409d1', 'Audio Recorder A', 'ACTIVE'),
('79a7d791-b72a-4083-a87e-7290d860cc66', 'Audio Recorder B', 'ACTIVE'),
('7ce02206-c8bb-4560-a60e-81421c559848', 'Laptop A', 'ACTIVE'),
('a8f6ec78-c765-4c23-a6b6-f25c34635269', 'Laptop B', 'ACTIVE'),
('be24c9d7-f118-4e5b-8d47-4656a01311b9', 'Tripod A', 'MAINTENANCE'),
('2bbd44ad-9728-439c-bc26-0119fd9d76be', 'Microphone Set A', 'RETIRED');

INSERT INTO reservation (id, equipment_id, start_time, end_time) VALUES
('2d8f4162-2b85-48d1-8b59-d2be0a6e512a', '8f6c7bbd-3f1f-4b6f-9bcb-84db99411f2a', '2026-06-01 09:00:00', '2026-06-01 11:00:00'),
('64f23768-2b8a-44f5-83d9-6a2e3c85daba', 'e3e4f267-5e88-4505-bad1-4a99c2b6050d', '2026-06-01 12:00:00', '2026-06-01 14:00:00'),
('2a080bf3-d457-4b70-bbe5-96ef56ce626e', '9a822a38-9151-4431-86f7-22ee16a0df86', '2026-06-02 09:00:00', '2026-06-02 11:00:00'),
('e7950745-5df0-4f64-bf44-4f0df07f2413', 'e24f6d98-645d-40f9-8f49-25ef49fb5d08', '2026-06-02 12:00:00', '2026-06-02 14:00:00'),
('ad5b4c5f-d43e-4f79-89db-777f2e7cc2ca', 'f8b0f702-9c34-497a-9d61-57e235f409d1', '2026-06-03 09:00:00', '2026-06-03 11:00:00');
