package psbp.external.specification.classification

import psbp.external.specification.identity.Identity

import psbp.external.specification.composition.Composition

trait Classification[>-->[- _, + _]]
  extends Identity[>-->]
  , Composition[>-->]