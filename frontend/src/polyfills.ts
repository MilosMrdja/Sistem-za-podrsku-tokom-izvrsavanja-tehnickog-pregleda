/** sockjs-client očekuje Node `global` u browseru */
;(globalThis as typeof globalThis & { global: typeof globalThis }).global =
  globalThis;
